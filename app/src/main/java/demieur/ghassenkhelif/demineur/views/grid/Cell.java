package demieur.ghassenkhelif.demineur.views.grid;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import demieur.ghassenkhelif.demineur.GameEngine;
import demieur.ghassenkhelif.demineur.R;

/**
 * Created by Ghassen on 05/02/2017.
 */

public class Cell extends BaseCell implements View.OnClickListener , View.OnLongClickListener{

    public Cell( Context context , int x , int y ){
        super(context);

        setPosition(x,y);

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawButton(canvas);

        if ((isFlagged())){
            drawFlag(canvas);
        }else if (isRevealed() & isBomb() && !isClicked()){
            drawBomb(canvas);
        }else {
            if (isClicked()){
                if (getValue() == -1){
                    drawExploded(canvas);
                }else {
                    drawNumber(canvas);
                }
            }else {
                drawButton(canvas);
            }
        }
    }

    private void drawExploded(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.mineex );
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }
    private void drawFlag(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.flag );
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }
    private void drawButton(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.btn );
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawBomb(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.mine );
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }



    private void drawNumber(Canvas canvas){
        Drawable drawable = null;
        switch (getValue()){
            case 0:
                drawable = ContextCompat.getDrawable(getContext(),R.drawable.btn0);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(),R.drawable.btn1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(),R.drawable.btn2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(),R.drawable.btn3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(),R.drawable.btn4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(),R.drawable.btn5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(),R.drawable.btn6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(),R.drawable.btn7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(),R.drawable.btn8);
                break;
        }
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    @Override
    public void onClick(View v) {
        GameEngine.getInstance().click(getXPos(),getYPos());

    }

    @Override
    public boolean onLongClick(View v) {
        GameEngine.getInstance().flag(getXPos(),getYPos());
        return true;
    }
}
