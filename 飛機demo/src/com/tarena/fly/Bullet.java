package com.tarena.fly;

import java.awt.image.BufferedImage;

/**
 * 子彈類:是飛行物
 */
public class Bullet extends FlyingObject {
	private int speed = 3;  //移動的速度
	private BufferedImage[] images = {};  //英雄機圖片
	private int index = 0;                //英雄機圖片切換索引
	
	/** 初始化數據 */
	public Bullet(int x,int y){
		this.x = x;
		this.y = y;
		images = new BufferedImage[]{ShootGame.bullet_1, ShootGame.bullet_2, ShootGame.bullet_3}; //英雄機圖片數組
		this.image = ShootGame.bullet_1;
	}

	/** 移動 */
	@Override
	public void step(){   
		y-=speed;
		if(images.length>0){
			image = images[index++/15%images.length];  //切換圖片bullet,bullet1,bullet2
		}
	}

	/** 越界處理 */
	@Override
	public boolean outOfBounds() {
		return y<-height;
	}

}
