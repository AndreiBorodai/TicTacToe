package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private boolean IsOTurn = true;
    private TextView turnText;
    private ArrayList<GridButton> buttons = new ArrayList<GridButton>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turnText = findViewById(R.id.turnText);
        for (int i = 0; i < ((GridLayout)findViewById(R.id.TicTacToeGrid)).getChildCount(); i++)
        {
            buttons.add((GridButton) ((GridLayout)findViewById(R.id.TicTacToeGrid)).getChildAt(i));
            buttons.get(i).setColumnAndRowOfElementByIndex(i);
            buttons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if(((GridButton)view).isPressedByO() == null)
                    {
                        ((GridButton)view).setPressedByO(IsOTurn);
                        if(isWin(((GridButton)view)))
                            win();
                        else if(isTie())
                            tie();
                        else
                            changeTurnSide();

                    }
                }
            });
        }
    }
    private void changeBtnColor(GridButton btn, int colorResource)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            btn.getBackground().setColorFilter(ContextCompat.getColor(this, colorResource), PorterDuff.Mode.MULTIPLY);
        }
        else
        {
            btn.setBackgroundColor(colorResource);
        }
    }
    private void changeTurnSide()
    {
        IsOTurn = !IsOTurn;
        turnText.setText(IsOTurn ? R.string.oTurn : R.string.xTurn);
    }

    private boolean isTie()
    {
        for (GridButton btn:buttons)
        {
            if(btn.isPressedByO() == null)
                return false;
        }
        return true;
    }
    private void paintWinningLine(int linecode)
    {
        switch (linecode)
        {
            case 7:
                for (int i = 0; i < 3; i++)
                {
                    changeBtnColor(getButtonByRowAndColumn(i, 2 - i), getResources().getColor(R.color.hightlighted));
                }
                break;
            case 6:
                for (int i = 0; i < 3; i++)
                {
                    changeBtnColor(getButtonByRowAndColumn(i, i), getResources().getColor(R.color.hightlighted));
                }
                break;
            case 5:
            case 4:
            case 3:
                for (int i = 0; i < 3; i++)
                {
                    changeBtnColor(getButtonByRowAndColumn(linecode - 3, i), getResources().getColor(R.color.hightlighted));
                }
                break;
            case 2:
            case 1:
            case 0:
                for (int i = 0; i < 3; i++)
                {
                    changeBtnColor(getButtonByRowAndColumn(i, linecode), getResources().getColor(R.color.hightlighted));
                }
                break;
        }

    }
    private void resetAllPaint()
    {
        for (GridButton btn:buttons)
        {
            changeBtnColor(btn, getResources().getColor(R.color.defualtBtnBackground));
        }
    }
    private boolean isWin(GridButton pressedButton)
    {
        for (int i = 0; i < 3; i++)
        {
            if(getButtonByRowAndColumn(i, pressedButton.getColumn()).isPressedByO() == null || getButtonByRowAndColumn(i, pressedButton.getColumn()).isPressedByO() != IsOTurn)
                break;
            else if(i == 2)
            {
                paintWinningLine(pressedButton.getColumn());
                return true;
            }
        }
        for (int i = 0; i < 3; i++)
        {
            if(getButtonByRowAndColumn(pressedButton.getRow(), i).isPressedByO() == null || getButtonByRowAndColumn(pressedButton.getRow(), i).isPressedByO() != IsOTurn)
                break;
            else if(i == 2)
            {
                paintWinningLine(pressedButton.getRow() + 3);
                return true;
            }
        }
        if(pressedButton.getRow() == pressedButton.getColumn())
        {
            for (int i = 0; i < 3; i++)
            {
                if(getButtonByRowAndColumn(i, i).isPressedByO() == null || getButtonByRowAndColumn(i, i).isPressedByO() != IsOTurn)
                    break;
                else if(i == 2)
                {
                    paintWinningLine(6);
                    return true;
                }
            }
        }
        if(pressedButton.getRow() + pressedButton.getColumn() == 2)
        {
            for (int i = 0; i < 3; i++)
            {
                if(getButtonByRowAndColumn(i, 2 - i).isPressedByO() == null || getButtonByRowAndColumn(i, 2 - i).isPressedByO() != IsOTurn)
                    break;
                else if(i == 2)
                {
                    paintWinningLine(7);
                    return true;
                }
            }
        }
        return false;
    }

    private GridButton getButtonByRowAndColumn(int row, int column)
    {
        for (GridButton btn:buttons)
        {
            if(btn.getColumn() == column && btn.getRow() == row)
                return btn;
        }
        return null;
    }

    private void win()
    {
        Toast.makeText(MainActivity.this, (IsOTurn ? R.string.oVictory : R.string.xVictory), Toast.LENGTH_LONG).show();
        Utils.delay(3, new Utils.DelayCallback() {
            @Override
            public void afterDelay() {
                resetGame();
            }
        });
    }
    private void tie()
    {
        Toast.makeText(MainActivity.this, R.string.tie, Toast.LENGTH_LONG).show();
        Utils.delay(3, new Utils.DelayCallback() {
            @Override
            public void afterDelay() {
                resetGame();
            }
        });
    }
    private void resetGame()
    {
        resetAllPaint();
        for (GridButton btn:buttons)
        {
            btn.setPressedByO(null);
        }
    }

}