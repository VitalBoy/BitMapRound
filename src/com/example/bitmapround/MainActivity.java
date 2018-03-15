package com.example.bitmapround;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.PorterDuff.Mode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener
{
	ImageView mImageView;
	private final int LEFT_TOP = 1;
	private final int LEFT_BOTTOM = 3;
	private final int RIGHT_TOP = 2;
	private final int RIGHT_BOTTOM = 4;
	private final int ALL = 0;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		initView();
	}

	private void initView()
	{
		// TODO Auto-generated method stub
		Button leftTop = ( Button ) findViewById( R.id.left_top );
		Button rightTop = ( Button ) findViewById( R.id.right_top );
		Button leftBottom = ( Button ) findViewById( R.id.left_bottom );
		Button rightBottom = ( Button ) findViewById( R.id.right_bottom );
		Button all = ( Button ) findViewById( R.id.all );
		mImageView = ( ImageView ) findViewById( R.id.show_iv );
		leftTop.setOnClickListener( this );
		rightTop.setOnClickListener( this );
		leftBottom.setOnClickListener( this );
		rightBottom.setOnClickListener( this );
		all.setOnClickListener( this );
	}

	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		switch( v.getId() )
		{
		case R.id.all:
			createRound( getResources(), R.drawable.image8, mImageView, 300, 3000, 9, 0 );
			break;
		case R.id.left_top:
			createRound( getResources(), R.drawable.image8, mImageView, 300, 3000, 9, 1 );

			break;
		case R.id.right_top:
			createRound( getResources(), R.drawable.image8, mImageView, 300, 3000, 9, 2 );

			break;
		case R.id.left_bottom:
			createRound( getResources(), R.drawable.image8, mImageView, 300, 3000, 9, 3 );

			break;
		case R.id.right_bottom:
			createRound( getResources(), R.drawable.image8, mImageView, 300, 3000, 9, 4 );

			break;

		default:
			break;
		}

	}

	// canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, paint);
	// // 异或,相同为0,不同为1
	// int notRoundedCorners = corners ^ CORNER_ALL;
	// //
	// 哪个角不是圆角再用小矩形画出来(原理类似图层加了一个白蒙板,然后用不透明度100%的黑色画笔去在蒙板上绘制,画笔所过之处你原来的图层就会显现出来)
	// try
	// {
	// if ((notRoundedCorners &; CORNER_TOP_LEFT) != 0)
	// {
	// if ((notRoundedCorners &; CORNER_TOP_RIGHT) != 0)
	// {
	// // 右上角恢复为直角
	// canvas.drawRect(mRect.right - cornerRadius, 0, mRect.right,
	// cornerRadius, paint);
	// }
	// if ((notRoundedCorners &; CORNER_BOTTOM_LEFT) != 0)
	// {

	// if ((notRoundedCorners &; CORNER_BOTTOM_RIGHT) != 0)
	// {
	// // 右下角恢复为直角
	// canvas.drawRect(mRect.right - cornerRadius, mRect.bottom -
	// cornerRadius, mRect.right, mRect.bottom,
	// paint);
	// }
	private void createRound( Resources resources, int image8, ImageView imageView, int width, int height,
					int roundSize, int leftOrRight )
	{
		// TODO Auto-generated method stub
		Bitmap bitmap = BitmapFactory.decodeResource( resources, image8 );
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		int bitmapWidth = bitmap.getWidth();
		int bitmapHeight = bitmap.getHeight();
		options.inSampleSize = 1;
		if ( bitmapWidth > width || bitmapHeight > height )
		{
			int widthZip = Math.round( bitmapWidth / width );
			int hightZip = Math.round( bitmapHeight / height );
			options.inSampleSize = widthZip < hightZip ? hightZip : widthZip;
		}
		options.inJustDecodeBounds = false;
		Bitmap bitmaps = BitmapFactory.decodeResource( resources, image8, options );

		createAllRound( bitmaps, roundSize, imageView, leftOrRight );

	}

	private void createAllRound( Bitmap bitmaps, int roundSize, ImageView imageView, int leftOrRight )
	{
		// TODO Auto-generated method stub
		Bitmap bitmap = Bitmap.createBitmap( bitmaps.getWidth(), bitmaps.getHeight(), Config.ARGB_8888 );
		Log.i( "Pineapple", "bitmap" + ( bitmap == null ) );
		Canvas canvas = new Canvas( bitmap );
		int color = 0xff000000;
		Paint paint = new Paint();
		Rect rect = new Rect( 0, 0, bitmaps.getWidth(), bitmaps.getHeight() );
		RectF rectF = new RectF( rect );
		paint.setAntiAlias( true );
		canvas.drawARGB( 0, 0, 0, 0 );
		paint.setColor( color );
		canvas.drawRoundRect( rectF, roundSize, roundSize, paint );
		switch( leftOrRight )
		{
		case 1:
			// // 左上角恢复为直角
			// canvas.drawRect(0, 0, cornerRadius, cornerRadius, paint);
			// }
			canvas.drawRect( 0, 0, roundSize, roundSize, paint );
			break;

		case 2:
			// // 右上角恢复为直角
			// canvas.drawRect(mRect.right - cornerRadius, 0, mRect.right,
			// cornerRadius, paint);
			canvas.drawRect( rectF.right - roundSize, 0, rectF.right, roundSize, paint );
			break;
		case 3:
			// // 左下角恢复为直角
			// canvas.drawRect(0, mRect.bottom - cornerRadius, cornerRadius,
			// mRect.bottom, paint);
			// }
			// float left, float top, float right, float bottom,
			canvas.drawRect( 0, rectF.bottom - roundSize, roundSize, rectF.bottom, paint );
			break;
		case 4:
			// 右下角恢复为直角
			// canvas.drawRect(mRect.right - cornerRadius, mRect.bottom -
			// cornerRadius, mRect.right, mRect.bottom,
			// paint);
			canvas.drawRect( rectF.right - roundSize, rectF.bottom - roundSize, rectF.right, rectF.bottom,
							paint );
			break;

		default:
			break;
		}
		paint.setXfermode( new PorterDuffXfermode( Mode.SRC_IN ) );//
		https: // www.cnblogs.com/libertycode/p/6290497.html
		canvas.drawBitmap( bitmaps, rect, rect, paint );//这个需要是传入的Bitmap否则无法显示;
		Log.i( "Pineapple", "bitmap" + ( bitmap == null ) );
		imageView.setImageBitmap( bitmap );
	}
}
