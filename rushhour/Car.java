package rushhour;

public class Car {
    private int start_row;
    private int start_column;
    private int end;
    private int length;
    private int orient;
    private String name;

    public Car(int row, int column, String name) {
        this.setStart_row(row);
        this.setStart_column(column);
        this.setName(name);
        this.setLength(1);
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart_column() {
        return start_column;
    }

    public void setStart_column(int start_column) {
        this.start_column = start_column;
    }

    public int getStart_row() {
        return start_row;
    }

    public void setStart_row(int start_row) {
        this.start_row = start_row;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrient() {
        return orient;
    }

    public void setOrient(int orient) {
        this.orient = orient;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void change(int r, int c) {
        if (r > this.start_row && c == this.start_column) {
            this.end = r;
            this.length++;
            this.orient = 0; // vertical
        } else if (r == this.start_row && c > this.start_column) {
            this.end = c;
            this.length++;
            this.orient = 1; // horizontal
        }
    }
    public boolean isTouch() {
        if(this.end >=5){
            return true;
        }
        return false;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Car)) {
            return false;
        }
        Car temp = (Car) obj;
        return this.start_row == temp.start_row && this.start_column == temp.start_column && this.length == temp.length
                && this.orient == temp.orient && this.name.equals(temp.name) && this.end == temp.end;
    }

    public Car(int start_row, int start_column, int end, int length, int orient, String name) {
        this.start_row = start_row;
        this.start_column = start_column;
        this.end = end;
        this.length = length;
        this.orient = orient;
        this.name = name;
    }

}
