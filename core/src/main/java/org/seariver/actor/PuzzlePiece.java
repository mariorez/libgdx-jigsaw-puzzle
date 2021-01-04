package org.seariver.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class PuzzlePiece extends DragAndDropActor {

    private int row;
    private int col;
    private PuzzleArea puzzleArea;

    public PuzzlePiece(float x, float y, Stage stage) {
        super(x, y, stage);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPuzzleArea(PuzzleArea puzzleArea) {
        this.puzzleArea = puzzleArea;
    }

    public PuzzleArea getPuzzleArea() {
        return puzzleArea;
    }

    public void clearPuzzleArea() {
        puzzleArea = null;
    }

    public boolean hasPuzzleArea() {
        return puzzleArea != null;
    }

    public boolean isCorrectlyPlaced() {
        return hasPuzzleArea()
                && this.getRow() == puzzleArea.getRow()
                && this.getCol() == puzzleArea.getCol();
    }

    public void onDragStart() {
        if (hasPuzzleArea()) {
            getPuzzleArea().setTargetable(true);
            clearPuzzleArea();
        }
    }

    public void onDrop() {
        if (hasDropTarget()) {
            PuzzleArea puzzleArea = (PuzzleArea) getDropTarget();
            moveToActor(puzzleArea);
            setPuzzleArea(puzzleArea);
            puzzleArea.setTargetable(false);
        }
    }
}
