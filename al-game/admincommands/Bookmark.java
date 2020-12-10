package admincommands;

class Bookmark {
  private String name;
  private float x;
  private float y;
  private float z;
  private int world_id;

  public Bookmark(float x, float y, float z, int world_id, String name) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.world_id = world_id;
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public float getX() {
    return this.x;
  }

  public float getY() {
    return this.y;
  }

  public float getZ() {
    return this.z;
  }

  public int getWorld_id() {
    return this.world_id;
  }
}
