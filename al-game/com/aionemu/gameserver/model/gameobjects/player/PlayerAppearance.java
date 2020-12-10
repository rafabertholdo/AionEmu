/*      */ package com.aionemu.gameserver.model.gameobjects.player;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PlayerAppearance
/*      */   implements Cloneable
/*      */ {
/*      */   private int face;
/*      */   private int hair;
/*      */   private int deco;
/*      */   private int tattoo;
/*      */   private int skinRGB;
/*      */   private int hairRGB;
/*      */   private int lipRGB;
/*      */   private int eyeRGB;
/*      */   private int faceShape;
/*      */   private int forehead;
/*      */   private int eyeHeight;
/*      */   private int eyeSpace;
/*      */   private int eyeWidth;
/*      */   private int eyeSize;
/*      */   private int eyeShape;
/*      */   private int eyeAngle;
/*      */   private int browHeight;
/*      */   private int browAngle;
/*      */   private int browShape;
/*      */   private int nose;
/*      */   private int noseBridge;
/*      */   private int noseWidth;
/*      */   private int noseTip;
/*      */   private int cheek;
/*      */   private int lipHeight;
/*      */   private int mouthSize;
/*      */   private int lipSize;
/*      */   private int smile;
/*      */   private int lipShape;
/*      */   private int jawHeigh;
/*      */   private int chinJut;
/*      */   private int earShape;
/*      */   private int headSize;
/*      */   private int neck;
/*      */   private int neckLength;
/*      */   private int shoulders;
/*      */   private int shoulderSize;
/*      */   private int torso;
/*      */   private int chest;
/*      */   private int waist;
/*      */   private int hips;
/*      */   private int armThickness;
/*      */   private int armLength;
/*      */   private int handSize;
/*      */   private int legThicnkess;
/*      */   private int legLength;
/*      */   private int footSize;
/*      */   private int facialRate;
/*      */   private int voice;
/*      */   private float height;
/*      */   
/*      */   public int getFace() {
/*   91 */     return this.face;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFace(int face) {
/*  102 */     this.face = face;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHair() {
/*  112 */     return this.hair;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHair(int hair) {
/*  123 */     this.hair = hair;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDeco() {
/*  133 */     return this.deco;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDeco(int deco) {
/*  144 */     this.deco = deco;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTattoo() {
/*  154 */     return this.tattoo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTattoo(int tattoo) {
/*  166 */     this.tattoo = tattoo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSkinRGB() {
/*  176 */     return this.skinRGB;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSkinRGB(int skinRGB) {
/*  187 */     this.skinRGB = skinRGB;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHairRGB() {
/*  197 */     return this.hairRGB;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHairRGB(int hairRGB) {
/*  208 */     this.hairRGB = hairRGB;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEyeRGB(int eyeRGB) {
/*  215 */     this.eyeRGB = eyeRGB;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEyeRGB() {
/*  223 */     return this.eyeRGB;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLipRGB() {
/*  233 */     return this.lipRGB;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLipRGB(int lipRGB) {
/*  244 */     this.lipRGB = lipRGB;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFaceShape() {
/*  255 */     return this.faceShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFaceShape(int faceShape) {
/*  266 */     this.faceShape = faceShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getForehead() {
/*  276 */     return this.forehead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setForehead(int forehead) {
/*  287 */     this.forehead = forehead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEyeHeight() {
/*  297 */     return this.eyeHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEyeHeight(int eyeHeight) {
/*  308 */     this.eyeHeight = eyeHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEyeSpace() {
/*  318 */     return this.eyeSpace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEyeSpace(int eyeSpace) {
/*  329 */     this.eyeSpace = eyeSpace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEyeWidth() {
/*  339 */     return this.eyeWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEyeWidth(int eyeWidth) {
/*  350 */     this.eyeWidth = eyeWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEyeSize() {
/*  360 */     return this.eyeSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEyeSize(int eyeSize) {
/*  372 */     this.eyeSize = eyeSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEyeShape() {
/*  382 */     return this.eyeShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEyeShape(int eyeShape) {
/*  394 */     this.eyeShape = eyeShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEyeAngle() {
/*  404 */     return this.eyeAngle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEyeAngle(int eyeAngle) {
/*  415 */     this.eyeAngle = eyeAngle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBrowHeight() {
/*  425 */     return this.browHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBrowHeight(int browHeight) {
/*  436 */     this.browHeight = browHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBrowAngle() {
/*  446 */     return this.browAngle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBrowAngle(int browAngle) {
/*  457 */     this.browAngle = browAngle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBrowShape() {
/*  467 */     return this.browShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBrowShape(int browShape) {
/*  478 */     this.browShape = browShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNose() {
/*  488 */     return this.nose;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNose(int nose) {
/*  499 */     this.nose = nose;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNoseBridge() {
/*  509 */     return this.noseBridge;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoseBridge(int noseBridge) {
/*  520 */     this.noseBridge = noseBridge;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNoseWidth() {
/*  530 */     return this.noseWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoseWidth(int noseWidth) {
/*  541 */     this.noseWidth = noseWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNoseTip() {
/*  551 */     return this.noseTip;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoseTip(int noseTip) {
/*  562 */     this.noseTip = noseTip;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCheek() {
/*  572 */     return this.cheek;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCheek(int cheek) {
/*  583 */     this.cheek = cheek;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLipHeight() {
/*  593 */     return this.lipHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLipHeight(int lipHeight) {
/*  604 */     this.lipHeight = lipHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMouthSize() {
/*  614 */     return this.mouthSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMouthSize(int mouthSize) {
/*  625 */     this.mouthSize = mouthSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLipSize() {
/*  635 */     return this.lipSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLipSize(int lipSize) {
/*  646 */     this.lipSize = lipSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSmile() {
/*  656 */     return this.smile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSmile(int smile) {
/*  667 */     this.smile = smile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLipShape() {
/*  677 */     return this.lipShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLipShape(int lipShape) {
/*  688 */     this.lipShape = lipShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getJawHeigh() {
/*  698 */     return this.jawHeigh;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setJawHeigh(int jawHeigh) {
/*  709 */     this.jawHeigh = jawHeigh;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getChinJut() {
/*  719 */     return this.chinJut;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setChinJut(int chinJut) {
/*  730 */     this.chinJut = chinJut;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEarShape() {
/*  740 */     return this.earShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEarShape(int earShape) {
/*  751 */     this.earShape = earShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeadSize() {
/*  761 */     return this.headSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHeadSize(int headSize) {
/*  772 */     this.headSize = headSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNeck() {
/*  782 */     return this.neck;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNeck(int neck) {
/*  793 */     this.neck = neck;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNeckLength() {
/*  803 */     return this.neckLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNeckLength(int neckLength) {
/*  814 */     this.neckLength = neckLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getShoulders() {
/*  824 */     return this.shoulders;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShoulders(int shoulders) {
/*  835 */     this.shoulders = shoulders;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getShoulderSize() {
/*  845 */     return this.shoulderSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShoulderSize(int shoulderSize) {
/*  856 */     this.shoulderSize = shoulderSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTorso() {
/*  867 */     return this.torso;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTorso(int torso) {
/*  878 */     this.torso = torso;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getChest() {
/*  888 */     return this.chest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setChest(int chest) {
/*  899 */     this.chest = chest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWaist() {
/*  909 */     return this.waist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWaist(int waist) {
/*  920 */     this.waist = waist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHips() {
/*  930 */     return this.hips;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHips(int hips) {
/*  941 */     this.hips = hips;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getArmThickness() {
/*  951 */     return this.armThickness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setArmThickness(int armThickness) {
/*  962 */     this.armThickness = armThickness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getArmLength() {
/*  972 */     return this.armLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setArmLength(int armLength) {
/*  983 */     this.armLength = armLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHandSize() {
/*  993 */     return this.handSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHandSize(int handSize) {
/* 1004 */     this.handSize = handSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLegThicnkess() {
/* 1014 */     return this.legThicnkess;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegThicnkess(int legThicnkess) {
/* 1025 */     this.legThicnkess = legThicnkess;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLegLength() {
/* 1035 */     return this.legLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegLength(int legLength) {
/* 1046 */     this.legLength = legLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFootSize() {
/* 1056 */     return this.footSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFootSize(int footSize) {
/* 1067 */     this.footSize = footSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFacialRate() {
/* 1077 */     return this.facialRate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFacialRate(int facialRate) {
/* 1088 */     this.facialRate = facialRate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getVoice() {
/* 1098 */     return this.voice;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVoice(int voice) {
/* 1109 */     this.voice = voice;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getHeight() {
/* 1119 */     return this.height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHeight(float height) {
/* 1130 */     this.height = height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/* 1140 */     Object newObject = null;
/*      */ 
/*      */     
/*      */     try {
/* 1144 */       newObject = super.clone();
/*      */     }
/* 1146 */     catch (CloneNotSupportedException e) {
/*      */       
/* 1148 */       e.printStackTrace();
/*      */     } 
/*      */     
/* 1151 */     return newObject;
/*      */   }
/*      */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\PlayerAppearance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */