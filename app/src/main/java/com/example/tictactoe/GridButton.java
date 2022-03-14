package com.example.tictactoe;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GridButton extends androidx.appcompat.widget.AppCompatButton
{
    private int column;
    private int row;
    private Boolean pressedByO;

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public GridButton(@NonNull Context context)
    {
        super(context);
    }

    public GridButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GridButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Boolean isPressedByO()
    {
        return pressedByO;
    }

    public void setPressedByO(Boolean pressedByO)
    {
        this.pressedByO = pressedByO;
        this.setText(pressedByO == null ? R.string.empty : (pressedByO ? R.string.O : R.string.X));
    }

    public void setColumnAndRowOfElementByIndex(int index)
    {
        column = index%3;
        row = index/3;
    }
}
