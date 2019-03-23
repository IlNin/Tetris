import java.util.*;

public class Pezzo {
 private ArrayList<Coordinate> elementi; // Ogni pezzo del Tetris e' formato da 4 elementi
 
 public Pezzo(int i) { 
  if (i == 0) { // Pezzo a cubo
   elementi = new ArrayList<Coordinate>();
   elementi.add(new Coordinate(1, 4)); // Centro del pezzo
   elementi.add(new Coordinate(1, 5));
   elementi.add(new Coordinate(0, 4));
   elementi.add(new Coordinate(0, 5)); }
  
  else if (i == 1) { // Pezzo a I
   elementi = new ArrayList<Coordinate>();
   elementi.add(new Coordinate(0, 4)); // Elemento pivot
   elementi.add(new Coordinate(0, 5));
   elementi.add(new Coordinate(0, 6));
   elementi.add(new Coordinate(0, 3)); }
   
  else if (i == 2) { // Pezzo a S
   elementi = new ArrayList<Coordinate>();
   elementi.add(new Coordinate(1, 4)); // Centro del pezzo
   elementi.add(new Coordinate(1, 3));
   elementi.add(new Coordinate(0, 4));
   elementi.add(new Coordinate(0, 5)); }
  
  else if (i == 3) { // Pezzo a Z
   elementi = new ArrayList<Coordinate>();
   elementi.add(new Coordinate(1, 4)); // Centro del pezzo
   elementi.add(new Coordinate(1, 5));
   elementi.add(new Coordinate(0, 4));
   elementi.add(new Coordinate(0, 3)); }
  
  else if (i == 4) { // Pezzo a _|_
   elementi = new ArrayList<Coordinate>();
   elementi.add(new Coordinate(1, 4)); // Centro del pezzo
   elementi.add(new Coordinate(1, 3)); 
   elementi.add(new Coordinate(1, 5));
   elementi.add(new Coordinate(0, 4)); }
  
  else if (i == 5) { // Pezzo a J
   elementi = new ArrayList<Coordinate>();
   elementi.add(new Coordinate(1, 4)); // Centro del pezzo
   elementi.add(new Coordinate(1, 3));
   elementi.add(new Coordinate(1, 5));
   elementi.add(new Coordinate(0, 3)); }
  
  else if (i == 6) { // Pezzo a L
   elementi = new ArrayList<Coordinate>();
   elementi.add(new Coordinate(1, 4)); // Centro del pezzo
   elementi.add(new Coordinate(1, 3));
   elementi.add(new Coordinate(1, 5));
   elementi.add(new Coordinate(0, 5)); } }
 
 public void coordinateRotazione(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) { // Cambia le coordinate dopo una rotazione
  elementi = new ArrayList<Coordinate>();
  elementi.add(new Coordinate(x1, y1)); 
  elementi.add(new Coordinate(x2, y2)); 
  elementi.add(new Coordinate(x3, y3));
  elementi.add(new Coordinate(x4, y4)); }
 
 public ArrayList<Coordinate> getElementi() {
  return elementi; }
}