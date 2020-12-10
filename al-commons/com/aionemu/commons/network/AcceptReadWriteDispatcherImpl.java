package com.aionemu.commons.network;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AcceptReadWriteDispatcherImpl extends Dispatcher {
  private final List<AConnection> pendingClose = new ArrayList<AConnection>();

  public AcceptReadWriteDispatcherImpl(String name, DisconnectionThreadPool dcPool) throws IOException {
    super(name, dcPool);
  }

  void dispatch() throws IOException {
    int selected = this.selector.select();

    processPendingClose();

    if (selected != 0) {

      Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
      while (selectedKeys.hasNext()) {

        SelectionKey key = selectedKeys.next();
        selectedKeys.remove();

        if (!key.isValid()) {
          continue;
        }

        switch (key.readyOps()) {

          case 16:
            accept(key);

          case 1:
            read(key);

          case 4:
            write(key);

          case 5:
            read(key);
            if (key.isValid()) {
              write(key);
            }
        }
      }
    }
  }

  void closeConnection(AConnection con) {
    synchronized (this.pendingClose) {

      this.pendingClose.add(con);
    }
  }

  private void processPendingClose() {
    synchronized (this.pendingClose) {

      for (AConnection connection : this.pendingClose)
        closeConnectionImpl(connection);
      this.pendingClose.clear();
    }
  }
}
