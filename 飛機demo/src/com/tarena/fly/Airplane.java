package com.tarena.fly;

import java.util.Random;

/**
 *  敵飛機: 是飛行物，也是敵人
 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 3;  //移動步驟
	
	/** 初始化數據 */
	public Airplane(){
		this.image = ShootGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;          
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);
	}
	
	/** 獲取分數 */
	@Override
	public int getScore() {  
		return 5;
	}

	/** //越界處理 */
	@Override
	public 	boolean outOfBounds() {   
		return y>ShootGame.HEIGHT;
	}

	/** 移動 */
	@Override
	public void step() {   
		y += speed;
	}

}

