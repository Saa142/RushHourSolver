package rushhour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Board {
    private int carCount;
    private List<String> key;
    private HashMap<String, Car> myCars;
    private String solution;
    private int fScore;
    private int gScore;

    public Board(HashMap<String, Car> cars, String sol) {
        this.myCars = cars;
        this.carCount = cars.size();
        this.generateKey();
        this.solution = sol;
    }

    public int getgScore() {
        return gScore;
    }

    public void setgScore(int gScore) {
        this.gScore = gScore;
    }

    public int getfScore() {
        return fScore;
    }

    public void setfScore(int fScore) {
        this.fScore = fScore;
    }

    public Board(String filePath) throws IOException {
        File myObj = new File(filePath);
        if (myObj.equals(null)) {
            throw new FileNotFoundException("File doesn't Find");
        }
        Scanner myReader = new Scanner(myObj);
        int x = 0;
        myCars = new HashMap<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (data.length() != 6) {
                myReader.close();
                throw new IOException("Bad File!!");
            }
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) != '.') {
                    this.addCar(String.valueOf(data.charAt(i)), x, i);
                }
            }
            x++;
        }
        this.carCount = myCars.size();
        myReader.close();
        this.generateKey();
        this.solution = "";
    }

    public List<Board> successor() {
        List<Board> res = new ArrayList<>();
        HashMap<String, Car> carCopy = new HashMap<>();
        for (String k : myCars.keySet()) {
            Car car = myCars.get(k);
            int r = car.getStart_row();
            int c = car.getStart_column();
            int l = car.getLength();
            int o = car.getOrient();
            if (o == 1) {// horizontal
                //// Move to the left
                if (c >= 1 && this.key.get(r).charAt(c - 1) == '.') {
                    Car newCar = new Car(r, c - 1, car.getEnd() - 1, car.getLength(), o, car.getName());
                    String sl= car.getName() + "L1\n";
                    carCopy = new HashMap<String, Car>(this.myCars);
                    carCopy.put(k, newCar);
                    res.add(new Board(carCopy, this.solution+sl));
                }
                //// Move to the right
                if (c + l <= 5 && this.key.get(r).charAt(c + l) == '.') {
                    Car newCar = new Car(r, c + 1, car.getEnd() + 1, car.getLength(), o, car.getName());
                    carCopy = new HashMap<String, Car>(this.myCars);
                    String sl= car.getName() + "R1\n";
                    carCopy.put(k, newCar);
                    res.add(new Board(carCopy, this.solution+ sl));
                }
            } else {
                //// Move to the up
                if (r >= 1 && this.key.get(r - 1).charAt(c) == '.') {
                    Car newCar = new Car(r - 1, c, car.getEnd() - 1, car.getLength(), o, car.getName());
                    carCopy = new HashMap<String, Car>(this.myCars);
                    String sl= car.getName() + "U1\n";
                    carCopy.put(k, newCar);
                    res.add(new Board(carCopy, this.solution+sl));
                }
                //// Move to the down
                if (r + l <= 5 && this.key.get(r + l).charAt(c) == '.') {
                    Car newCar = new Car(r + 1, c, car.getEnd() + 1, car.getLength(), o, car.getName());
                    carCopy = new HashMap<String, Car>(this.myCars);
                    String sl= car.getName() + "D1\n";
                    carCopy.put(k, newCar);
                    res.add(new Board(carCopy, this.solution+sl));
                }
            }
        }
        return res;
    }

    public boolean isGoal() {
        Car x = myCars.get("X");
        return x.isTouch();
    }

    public String getKey() {
        String res = "";
        for (int i = 0; i < this.key.size(); i++) {
            res += this.key.get(i);
        }
        return res;
    }
    public String getSloution(){
        return this.solution;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Board)) {
            return false;
        }
        Board b = (Board) obj;
        if (b.hashCode() != this.hashCode()) {
            return false;
        }
        if (this.carCount != b.carCount) {
            return false;
        }
        for (String k : myCars.keySet()) {
            if (!this.myCars.get(k).equals(b.myCars.get(k))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < this.key.size(); i++) {
            res += this.key.get(i) + "\n";
        }
        return res;
    }

    private void addCar(String cName, int r, int c) {
        if (myCars.containsKey(cName)) {
            myCars.get(cName).change(r, c);
        } else {
            Car car = new Car(r, c, cName);
            myCars.put(cName, car);
        }
    }

    private void generateKey() {
        this.key = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            this.key.add(i, "......");
        }
        for (Car temp : myCars.values()) {
            if (temp.getOrient() == 1) {// horizontal
                int tempRow = temp.getStart_row();
                StringBuilder myName = new StringBuilder(this.key.get(tempRow));
                for (int i = temp.getStart_column(); i <= temp.getEnd(); i++) {
                    myName.setCharAt(i, temp.getName().charAt(0));
                }
                this.key.set(tempRow, myName.toString());
            } else { // vertical
                int tempColumn = temp.getStart_column();
                StringBuilder myName;
                for (int i = temp.getStart_row(); i <= temp.getEnd(); i++) {
                    myName = new StringBuilder(this.key.get(i));
                    myName.setCharAt(tempColumn, temp.getName().charAt(0));
                    this.key.set(i, myName.toString());
                }
            }
        }
    }
    public int getFreedomCount(){
        int count  =0;
        for (String k : myCars.keySet()) {
            Car car = myCars.get(k);
            int r = car.getStart_row();
            int c = car.getStart_column();
            int l = car.getLength();
            int o = car.getOrient();
            if (o == 1) {// horizontal
                //// Move to the left
                if (c >= 1 && this.key.get(r).charAt(c - 1) == '.') {
                    count++;
                }
                //// Move to the right
                if (c + l <= 5 && this.key.get(r).charAt(c + l) == '.') {
                    count++;
                }
            } else {
                //// Move to the up
                if (r >= 1 && this.key.get(r - 1).charAt(c) == '.') {
                    count++;
                }
                //// Move to the down
                if (r + l <= 5 && this.key.get(r + l).charAt(c) == '.') {
                    count++;
                }
            }
        }
        return count;
    }

}
