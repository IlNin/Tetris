import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

public class Tetris extends JFrame {
 JPanel p; // Pannello principale a cui verranno aggiunti tutti gli altri componenti
 JPanel pGiocatore; // Pannello che conterra' la griglia di gioco
 JLabel scrittaGiocatore; // Label contenente il punteggio attuale
 JLabel[][] griglia; // Griglia di gioco
 JLabel scrittaProssimoPezzo; // Label che indica quale sara' il prossimo pezzo di gioco
 Pezzo pezzo; // Pezzo di gioco attuale
 int tipoPezzo; // Indica la tipologia del pezzo corrente
 int prossimoPezzo; // indica la tipologia del prossimo pezzo
 char orientamento; // Indica l'orientamento del pezzo
 int pivotX; // Posizione x dell'elemento su cui ruotare il blocco (solo blocchi a I)
 int pivotY; // Posizione y dell'elemento su cui ruotare il blocco (solo blocchi a I)
 int centroX; // Posizione x dell'elemento su cui ruotare il blocco (tutti gli altri blocchi)
 int centroY; // Posizione y dell'elemento su cui ruotare il blocco (tutti gli altri blocchi)
 java.util.Timer timer; // Timer che regola la caduta dei pezzi
 boolean scorriPezzo = true; // Indica se e' possibile scorrere in basso il pezzo
 int punteggio = 0; // Punteggio di gioco
 
 boolean giocoIniziato = false; // Indica se la partita e' iniziata
 boolean gameOver = false; // Indica se il gioco e' terminato
 boolean pausa = false; // Indica se la pausa e' stata attivata
 KeyListener inputTetris; // Contiene gli eventi relativi agli input della tastiera
 
 public Tetris() {
  setTitle("TETRIS"); // Titolo che appare in alto alla finestra
  p = new JPanel();
  p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS)); // Il pannello aggiungera' i componenti a colonna
  p.setVisible(true);
  this.setContentPane(p); // p diventa il pannello principale
  
  scrittaGiocatore = new JLabel("Premi 's' per iniziare!"); // Crea la label con il punteggio del giocatore (all'inizio conterra' il comando per iniziare)
  scrittaGiocatore.setAlignmentX(Component.CENTER_ALIGNMENT); // Piazza la label nel centro della riga
  p.add(scrittaGiocatore);
  
  pGiocatore = new JPanel(); // Crea il pannello che conterra' la griglia
  pGiocatore.setBackground(Color.BLACK); // Lo sfondo e' nero per distinguerlo dalle caselle bianche
  pGiocatore.setVisible(true);
  p.add(pGiocatore);
  
  scrittaProssimoPezzo = new JLabel(); // Crea la label che indica il prossimo pezzo
  scrittaProssimoPezzo.setAlignmentX(Component.CENTER_ALIGNMENT); // Piazza la label nel centro della riga
  p.add(scrittaProssimoPezzo);
  
  creaGriglia();
  inserisciPezzo(); 
  
  inputTetris = new KeyAdapter() {
   public void keyPressed(KeyEvent e) { 
    if (e.getKeyChar() == 's' && giocoIniziato == true && gameOver == false && pausa == false) { // Il giocatore tiene premuto il pulsante "s" per far scorrere i pezzi piu' velocemente
     timer.cancel(); // Crea un nuovo timer piu' veloce per far scorrere piu' in fretta i pezzi
     timer = new java.util.Timer();
     timer.schedule(new scorriPezzo(), 0, 50); } 
    else if (e.getKeyChar() == 'c' && giocoIniziato == true && gameOver == false && pausa == false) { // Il giocatore ruota il pezzo premendo "c"
     cambiaOrientamento(); } }
	 
   public void keyReleased(KeyEvent e) {
    if (e.getKeyChar() == 's' && giocoIniziato == true && gameOver == false && pausa == false) { // Il giocatore rilascia "s" e i pezzi scorrono alla velocita' di prima
     timer.cancel(); // Ripristina il timer originale
     timer = new java.util.Timer();
     timer.schedule(new scorriPezzo(), 0, 350); } }
	 
   public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() == 's' && giocoIniziato == false && gameOver == false) { // Avvia il gioco per la prima volta
     scrittaGiocatore.setText("Punteggio: " + punteggio);
     timer = new java.util.Timer();
     timer.schedule(new scorriPezzo(), 0, 350);
     giocoIniziato = true; }
    else if (e.getKeyChar() == 'a' && giocoIniziato == true && gameOver == false && pausa == false) { // Il giocatore preme "a" per spostare il blocco a sinistra
     spostaSinistra(); }
    else if (e.getKeyChar() == 'd' && giocoIniziato == true && gameOver == false && pausa == false) { // Il giocatore preme "d" per spostare il blocco a destra
     spostaDestra();  }
    else if (e.getKeyChar() == 'p' && giocoIniziato == true && gameOver == false && pausa == false) { // Il giocatore preme "s" per mettere in pausa
     timer.cancel();
     setTitle("PAUSA");
     pausa = true; }
    else if (e.getKeyChar() == 'p' && giocoIniziato == true && gameOver == false && pausa == true) { // Il giocatore preme "s" per togliere la pausa
     setTitle("TETRIS");
     timer = new java.util.Timer();
     timer.schedule(new scorriPezzo(), 0, 350);
     pausa = false;	 } } };
	 
  this.addKeyListener(inputTetris); }
 
 public void creaGriglia() { // Prepara la griglia sulla quale si svolge l'azione di gioco
  griglia = new JLabel[16][10];
  for (int i = 0; i < 16; i++) {
   for (int j = 0; j < 10; j++) {
    griglia[i][j] = new JLabel("     "); // Le caselle sono costituite da delle JLabel vuote colorate di bianco
    griglia[i][j].setBackground(Color.WHITE);
    griglia[i][j].setOpaque(true);
    pGiocatore.add(griglia[i][j]); } } }
	
 public void inserisciPezzo() { // Inserisce un nuovo pezzo in cima alla griglia
  orientamento = 'e'; // Orientamento di default quando un nuovo pezzo viene generato
  if (giocoIniziato == false) { // A inizio gioco genera il pezzo iniziale e quello successivo
   Random randInt = new Random();
   Random randInt2 = new Random();
   tipoPezzo = randInt.nextInt(7);
   pezzo = new Pezzo(tipoPezzo);
   prossimoPezzo = randInt2.nextInt(7); }
  else { // Altrimenti il pezzo successivo diventa quello attuale e viene generato un nuovo pezzo successivo
   Random randInt = new Random();
   tipoPezzo = prossimoPezzo;
   pezzo = new Pezzo(tipoPezzo);
   prossimoPezzo = randInt.nextInt(7); }
  
  if (prossimoPezzo == 0) { // Aggiorna la label contenente il prossimo pezzo
   scrittaProssimoPezzo.setText("Prossimo pezzo: [ ]"); }
  else if (prossimoPezzo == 1) {
   scrittaProssimoPezzo.setText("Prossimo pezzo: ____"); }
  else if (prossimoPezzo == 2) {
   scrittaProssimoPezzo.setText("Prossimo pezzo: _|\u203E"); }
  else if (prossimoPezzo == 3) {
   scrittaProssimoPezzo.setText("Prossimo pezzo: \u203E|_"); }
  else if (prossimoPezzo == 4) {
   scrittaProssimoPezzo.setText("Prossimo pezzo: _|_"); }
  else if (prossimoPezzo == 5) {
   scrittaProssimoPezzo.setText("Prossimo pezzo: |__"); }
  else if (prossimoPezzo == 6) {
   scrittaProssimoPezzo.setText("Prossimo pezzo: __|"); }
   
  centroX = 1;
  centroY = 4;
  pivotX = 0;
  pivotY = 4;
  if (tipoPezzo == 0) { // Pezzo a cubo
   if (griglia[0][4].getBackground() == Color.WHITE && griglia[0][5].getBackground() == Color.WHITE && griglia[1][4].getBackground() == Color.WHITE && griglia[1][5].getBackground() == Color.WHITE) {
    griglia[0][4].setBackground(Color.RED); // Per ogni pezzo controlla se c'e' spazio prima di generarlo, altrimenti il gioco finisce
    griglia[0][5].setBackground(Color.RED);
    griglia[1][4].setBackground(Color.RED);
    griglia[1][5].setBackground(Color.RED); }
   else {
    gameOver(); } }
  else if (tipoPezzo == 1) { // Pezzo a "I"
   if (griglia[0][6].getBackground() == Color.WHITE && griglia[0][5].getBackground() == Color.WHITE && griglia[0][4].getBackground() == Color.WHITE && griglia[0][3].getBackground() == Color.WHITE) {
    griglia[0][6].setBackground(Color.RED);
    griglia[0][5].setBackground(Color.RED);
    griglia[0][4].setBackground(Color.RED);
    griglia[0][3].setBackground(Color.RED); }
   else {
    gameOver(); } }
  else if (tipoPezzo == 2) { // Pezzo a "S"
   if (griglia[1][4].getBackground() == Color.WHITE && griglia[1][3].getBackground() == Color.WHITE && griglia[0][4].getBackground() == Color.WHITE && griglia[0][5].getBackground() == Color.WHITE) {
    griglia[1][4].setBackground(Color.RED);
    griglia[1][3].setBackground(Color.RED);
    griglia[0][4].setBackground(Color.RED);
    griglia[0][5].setBackground(Color.RED); }
   else {
    gameOver(); } }
  else if (tipoPezzo == 3) { // Pezzo a "Z"
   if (griglia[1][4].getBackground() == Color.WHITE && griglia[1][5].getBackground() == Color.WHITE && griglia[0][4].getBackground() == Color.WHITE && griglia[0][3].getBackground() == Color.WHITE) {
    griglia[1][4].setBackground(Color.RED);
    griglia[1][5].setBackground(Color.RED);
    griglia[0][4].setBackground(Color.RED);
    griglia[0][3].setBackground(Color.RED); }
   else {
    gameOver(); } }
  else if (tipoPezzo == 4) { // Pezzo a _|_
   if (griglia[1][4].getBackground() == Color.WHITE && griglia[1][3].getBackground() == Color.WHITE && griglia[1][5].getBackground() == Color.WHITE && griglia[0][4].getBackground() == Color.WHITE) {
    griglia[1][4].setBackground(Color.RED);
    griglia[1][3].setBackground(Color.RED);
    griglia[1][5].setBackground(Color.RED);
    griglia[0][4].setBackground(Color.RED); }
   else {
    gameOver(); } }
  else if (tipoPezzo == 5) { // Pezzo a J
   if (griglia[1][4].getBackground() == Color.WHITE && griglia[1][3].getBackground() == Color.WHITE && griglia[1][5].getBackground() == Color.WHITE && griglia[0][3].getBackground() == Color.WHITE) {
    griglia[1][4].setBackground(Color.RED);
    griglia[1][3].setBackground(Color.RED);
    griglia[1][5].setBackground(Color.RED);
    griglia[0][3].setBackground(Color.RED); }
   else {
    gameOver(); } }
  else if (tipoPezzo == 6) { // Pezzo a L
   if (griglia[1][4].getBackground() == Color.WHITE && griglia[1][3].getBackground() == Color.WHITE && griglia[1][5].getBackground() == Color.WHITE && griglia[0][5].getBackground() == Color.WHITE) {
    griglia[1][4].setBackground(Color.RED);
    griglia[1][3].setBackground(Color.RED);
    griglia[1][5].setBackground(Color.RED);
    griglia[0][5].setBackground(Color.RED); }
   else {
    gameOver(); } } }
	
 public void gameOver() { // Il gioco termina!
  gameOver = true;
  timer.cancel();
  String[] opzioni = {"Nuova Partita", "Menu"}; // Chiede al giocatore se vuole fare un'altra partita o tornare al menu 
  int scelta = JOptionPane.showOptionDialog(null, new JLabel("Game Over!", JLabel.CENTER), "TETRIS", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, opzioni, opzioni[0]);
  if (scelta == 0) {
   inizioNuovaPartita(); }
  else {
   apriMenu();  } }
 
 public void inizioNuovaPartita() { // Inizia una nuova partita
  Tetris tetris = new Tetris();
  tetris.setSize(210,400);
  tetris.setResizable(false);
  tetris.setVisible(true);
  tetris.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
  setVisible(false);
  dispose(); }
 
 public void apriMenu() { // Torna al menu
  Menu menu = new Menu();
  menu.setSize(530,248);
  menu.setResizable(false);
  menu.setVisible(true);
  menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  dispose();  }
  
 public void cancellaFlag() { // Ripristina i flag sul pezzo corrente
  Iterator it = pezzo.getElementi().iterator();
  while (it.hasNext()) {
   Coordinate correnti = (Coordinate) it.next();
   int X = correnti.getX();
   int Y = correnti.getY();
   griglia[X][Y].setName(""); } }
 
 public boolean controllaSpazioSinistra() { // Controlla se il pezzo si puo' spostare a sinistra
  Iterator it = pezzo.getElementi().iterator();
  while (it.hasNext()) { // Scorre tutti gli elementi del pezzo
   Coordinate correnti = (Coordinate) it.next();
   int X = correnti.getX(); // Ottiene le coordinate dell'elemento attuale
   int Y = correnti.getY();
   if (Y == 0 || griglia[X][Y-1].getBackground() == Color.BLUE) { // Controlla se la griglia e' finita o se il posto a sinistra e' occupato
    return false; } }
  return true; }
 
 public synchronized void spostaSinistra() { // Sposta il pezzo attuale a sinistra
  scorriPezzo = false; // Blocca il pezzo prima di spostarlo
  if (controllaSpazioSinistra() == true) { // Controlla prima se c'è lo spazio
   Iterator it = pezzo.getElementi().iterator();
   while (it.hasNext()) { // Scorre tutti gli elementi
    Coordinate correnti = (Coordinate) it.next();
    int X = correnti.getX(); // Ottiene le coordinate dell'elemento attuale
    int Y = correnti.getY();
    if (griglia[X][Y].getName() != "Flag") { // "Flag" indica che nella casella attuale e' gia' stato spostato un elemento del pezzo attuale. Non va quindi colorata di bianco!
     griglia[X][Y].setBackground(Color.WHITE); } // Colora di bianco la casella, in quanto l'elemento scala di una posizione
    if (griglia[X][Y-1].getBackground() == Color.RED) { // La casella accanto e' gia' colorata di rosso: setta il flag!
     griglia[X][Y-1].setName("Flag"); }
    else { // Altrimenti la colora di rosso
     griglia[X][Y-1].setBackground(Color.RED); }
    correnti.setY(Y-1);	} // Aggiorna le coordinate
   cancellaFlag(); // Ripristina i flag
   centroY = centroY-1;
   pivotY = pivotY-1; }
  scorriPezzo = true; } // Permette nuovamente lo scorrimento del pezzo verso il basso
	
 public boolean controllaSpazioDestra() { // Come prima, ma verso destra
  Iterator it = pezzo.getElementi().iterator();
  while (it.hasNext()) {
   Coordinate correnti = (Coordinate) it.next();
   int X = correnti.getX();
   int Y = correnti.getY();
   if (Y == 9 || griglia[X][Y+1].getBackground() == Color.BLUE) {
    return false; } }
  return true; }
  
 public synchronized void spostaDestra() { // Come prima, ma verso destra
  scorriPezzo = false;
  if (controllaSpazioDestra() == true) {
   Iterator it = pezzo.getElementi().iterator();
   while (it.hasNext()) {
    Coordinate correnti = (Coordinate) it.next();
    int X = correnti.getX();
    int Y = correnti.getY();
    if (griglia[X][Y].getName() != "Flag") {
     griglia[X][Y].setBackground(Color.WHITE); }
    if (griglia[X][Y+1].getBackground() == Color.RED) {
     griglia[X][Y+1].setName("Flag"); }
    else {
     griglia[X][Y+1].setBackground(Color.RED); }
    correnti.setY(Y+1); }
   cancellaFlag();
   centroY = centroY+1;
   pivotY = pivotY+1; }
  scorriPezzo = true; }
 
 public boolean controllaSpazioSotto() { // Come prima, ma verso il basso
  Iterator it = pezzo.getElementi().iterator();
  while (it.hasNext()) {
   Coordinate correnti = (Coordinate) it.next();
   int X = correnti.getX();
   int Y = correnti.getY();
   if (X == 15 || griglia[X+1][Y].getBackground() == Color.BLUE) {
    return false; } }
  return true; }
	  
 class scorriPezzo extends TimerTask { // Animazione dei pezzi che cadono
  public synchronized void run() {
   if (scorriPezzo = true) {
    if (controllaSpazioSotto() == true) { // Scorre i pezzi verso il basso
     Iterator it = pezzo.getElementi().iterator();
     while (it.hasNext()) {
      Coordinate correnti = (Coordinate) it.next();
      int X = correnti.getX();
      int Y = correnti.getY();
      if (griglia[X][Y].getName() != "Flag") {
       griglia[X][Y].setBackground(Color.WHITE); }
      if (griglia[X+1][Y].getBackground() == Color.RED) {
       griglia[X+1][Y].setName("Flag"); }
      else {
       griglia[X+1][Y].setBackground(Color.RED); }
      correnti.setX(X+1); }
     cancellaFlag();
     centroX = centroX+1;
     pivotX = pivotX+1; }
  
    else { // I pezzi toccano terra e diventano blu
     Iterator it = pezzo.getElementi().iterator();
     while (it.hasNext()) {
      Coordinate correnti = (Coordinate) it.next();
      int X = correnti.getX();
      int Y = correnti.getY();
      griglia[X][Y].setBackground(Color.BLUE); }
      controllaGriglia();
      inserisciPezzo(); } } } }
	
 public synchronized void cambiaOrientamento() {
  scorriPezzo = false;
  if (tipoPezzo == 1) { // I pezzi a forma di I ruotano in base all'elemento pivot
   if (orientamento == 'n') {
    if (pivotY != 0 && (pivotY <= 7) && griglia[pivotX-2][pivotY-1].getBackground() != Color.BLUE && griglia[pivotX-2][pivotY+1].getBackground() != Color.BLUE && griglia[pivotX-2][pivotY+2].getBackground() != Color.BLUE) {
     orientamento = 'e';
     griglia[pivotX][pivotY].setBackground(Color.WHITE);
     griglia[pivotX-1][pivotY].setBackground(Color.WHITE);
     griglia[pivotX-3][pivotY].setBackground(Color.WHITE);
     griglia[pivotX-2][pivotY-1].setBackground(Color.RED);
     griglia[pivotX-2][pivotY+1].setBackground(Color.RED);
     griglia[pivotX-2][pivotY+2].setBackground(Color.RED);
     pezzo.coordinateRotazione(pivotX-2, pivotY-1, pivotX-2, pivotY, pivotX-2, pivotY+1, pivotX-2, pivotY+2);
     pivotX = pivotX-2; } }
   else if (orientamento == 'e') {
    if (pivotX <= 13 && pivotX >= 1 && griglia[pivotX+1][pivotY].getBackground() != Color.BLUE && griglia[pivotX-1][pivotY].getBackground() != Color.BLUE && griglia[pivotX+2][pivotY].getBackground() != Color.BLUE) {
     orientamento = 'n';
     griglia[pivotX][pivotY-1].setBackground(Color.WHITE);
     griglia[pivotX][pivotY+1].setBackground(Color.WHITE);
     griglia[pivotX][pivotY+2].setBackground(Color.WHITE);
     griglia[pivotX-1][pivotY].setBackground(Color.RED);
     griglia[pivotX+1][pivotY].setBackground(Color.RED);
     griglia[pivotX+2][pivotY].setBackground(Color.RED);
     pezzo.coordinateRotazione(pivotX+2, pivotY, pivotX, pivotY, pivotX+1, pivotY, pivotX-1, pivotY);
     pivotX = pivotX+2; } } }
  
  else if (tipoPezzo > 1) { // Tutti gli altri pezzi usano invece un metodo in comune basato sull'elemento Centro
   if (centroY >= 1 && centroY <= 8 && centroX >= 1 && centroY <= 14) {
    char[][] grigliaInvertita = new char[3][3]; // Creo una matrice di 3x3 attorno all'elemento centrale, in cui le righe e le colonne sono invertite
    int X = 0; 
    int Y = 0; 
    for (int i = centroX-1; i <= centroX+1; i++) { 
     for (int j = centroY-1; j <= centroY+1; j++) {
      if (griglia[i][j].getBackground() == Color.RED) {
       grigliaInvertita[Y][X] = 'x'; }
      else {
       grigliaInvertita[Y][X] = 'o'; }
      Y = Y+1; }
     Y = 0;
     X = X+1; }
	
    Y = 0; // Inverto la prima riga con la terza
    for (int j = 0; j <= 2; j++) { 
     char swap = grigliaInvertita[0][j];
     grigliaInvertita[0][j] = grigliaInvertita[2][j];
     grigliaInvertita[2][j] = swap;
     Y = Y+1; } // Ho ora ottenuto una matrice contenente la simulazione della rotazione del pezzo attuale
	
    X = 0; // Controllo se il pezzo si puo' girare confrontando la matrice invertita con il pezzo di griglia corrispondente
    Y = 0;
    for (int i = centroX-1; i <= centroX+1; i++) { 
     for (int j = centroY-1; j <= centroY+1; j++) {
      if (griglia[i][j].getBackground() == Color.BLUE && grigliaInvertita[X][Y] == 'x') {
       return; } // COLLISIONE: il pezzo NON si puo' ruotare
      else {
       Y = Y+1; } }
     Y = 0;
     X = X+1; }
	
    X = 0; // Il pezzo si puo' ruotare
    Y = 0;
    if (orientamento == 'e') { // Aggiorna l'orientamento del pezzo
     orientamento = 'n'; }
    else if (orientamento == 'n') {
     orientamento = 'w'; }
    else if (orientamento == 'w') {
     orientamento = 's'; }
    else if (orientamento == 's') {
     orientamento = 'e'; }
    Iterator it = pezzo.getElementi().iterator(); // Effettua la rotazione sulla griglia di gioco
    for (int i = centroX-1; i <= centroX+1; i++) { 
     for (int j = centroY-1; j <= centroY+1; j++) {
      if (grigliaInvertita[X][Y] == 'x') {
       Coordinate attuali = (Coordinate) it.next();
       attuali.setX(i);
       attuali.setY(j);
       griglia[i][j].setBackground(Color.RED); }
      else if (griglia[i][j].getBackground() != Color.BLUE) {
       griglia[i][j].setBackground(Color.WHITE); }
      Y = Y+1; }
     Y = 0;
     X = X+1; } } }
  scorriPezzo = true; }
	
 public synchronized void controllaGriglia() { // Controlla se alcune righe sono completamente blu
  for (int i = 0; i < 16; i++) {
   boolean rigaPiena = true;
   for (int j = 0; j < 10; j++) {
    if (griglia[i][j].getBackground() != Color.BLUE) {
     rigaPiena = false; } }
    if (rigaPiena == true) { // La riga e' blu, quando scalo la griglia con le posizioni precedenti
     punteggio = punteggio + 10;
     for (int x = i; x > 0; x--) {
      for (int y = 0; y < 10; y++) {
       griglia[x][y].setBackground(griglia[x-1][y].getBackground()); } } 
     controllaGriglia(); } } // Ricontrolla la griglia nel caso ci siano molteplici righe blu
  scrittaGiocatore.setText("Punteggio: " + punteggio); } } // Aggiorna il punteggio
