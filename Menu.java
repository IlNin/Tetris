import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

public class Menu extends JFrame {
 JPanel p; // Pannello principale a cui verranno aggiunti tutti gli altri componenti
 JPanel pannello; // Pannello che conterra' la griglia di gioco
 JPanel pulsanti; // Pannello che conterra' i pulsanti del menu
 JLabel griglia[][]; // Griglia di gioco attuale
 char input; // Input del giocatore, che viene simulato nel menu per creare l'animazione
 java.util.Timer timer; // Timer che regola l'animazione
 Pezzo pezzo; // Pezzo di gioco
 int tipoPezzo; // Indica la tipologia del pezzo corrente
 int pivotX; // Posizione x dell'elemento su cui ruotare il blocco (solo blocchi a I)
 int pivotY; // Posizione y dell'elemento su cui ruotare il blocco (solo blocchi a I)
 int centroX; // Posizione x dell'elemento su cui ruotare il blocco (tutti gli altri blocchi)
 int centroY; // Posizione y dell'elemento su cui ruotare il blocco (tutti gli altri blocchi)
 char orientamento; // Indica l'orientamento del pezzo
 int contatorePezzo = 1; // Indica il numero del pezzo che attualmente sta svolgendo l'animazione
 
 int i; // i e j utilizzati per creare la linea blu a fine animazione
 int j;
 
 public Menu() {
  setTitle("TETRIS - Menu Principale"); // Titolo che appare in alto alla finestra
  p = new JPanel(); 
  p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS)); // Il pannello aggiungera' i componenti a colonna
  p.setVisible(true);
  this.setContentPane(p); // p diventa il pannello principale
 
  pannello = new JPanel(); // Crea il pannello che conterra' la griglia
  pannello.setBackground(Color.BLACK); // Lo sfondo e' nero per distinguerlo dalle caselle bianche
  pannello.setVisible(true);
  p.add(pannello);
  
  pulsanti = new JPanel(); // Crea il pannello dove vengono inseriti i pulsanti
  pulsanti.setVisible(true);
  pulsanti.setLayout(new BoxLayout(pulsanti, BoxLayout.LINE_AXIS)); // Il pannello aggiungera' i componenti in sequenza su una riga
  p.add(pulsanti);
  
  JButton gioca = new JButton("Gioca"); // Crea e aggiunge i 3 pulsanti
  pulsanti.add(gioca);
  JButton about = new JButton("About");
  pulsanti.add(about);
  JButton esci = new JButton("Esci");
  pulsanti.add(esci);
  
  gioca.addActionListener(new ActionListener() { // Associa degli eventi ai pulsanti quando vengono premuti
   public void actionPerformed(ActionEvent e) {
    Tetris tetris = new Tetris(); // Avvia Tetris in una nuova finestra
    tetris.setSize(210,400);
    tetris.setResizable(false);
    tetris.setVisible(true);
    tetris.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	setVisible(false);
	dispose(); } }); // Chiude la finestra attuale contenente il menu
  
  about.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(null, "- BENVENUTO A TETRIS! -\n\n- REGOLE -\nSposta e ruota blocchi cadenti con precisone chirurgica,\ncercando di occupare un'intera riga e fare nuovo spazio\nnella griglia. Il gioco finisce quando la schermata si\nriempe di blocchi. Buona fortuna e riflessi saldi! \n\n- COMANDI -\n'a': Muovi il blocco a sinistra.\n'd': Muovi il blocco a destra.\n's': Fai scorrere il blocco piu' velocemente.\n'c': Cambia l'orientamento del blocco.\n\n>>> Creato da Luca Sannino, Settembre 2016 <<<", "Tetris - About", JOptionPane.PLAIN_MESSAGE); } });
  
  esci.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    timer.cancel();
    setVisible(false);
	dispose(); } });
  
  preparaGriglia();
  inserisciPezzo(); }
  
 public void preparaGriglia() { // Prepara la griglia sulla quale avviene l'animazione di apertura
  griglia = new JLabel[9][26];
  for (int i = 0; i < 9; i++) {
   for (int j = 0; j < 26; j++) {
    griglia[i][j] = new JLabel("     "); // Le caselle sono costituite da delle JLabel vuote colorate di bianco
	griglia[i][j].setBackground(Color.WHITE);
	griglia[i][j].setOpaque(true);
    pannello.add(griglia[i][j]); } } }

 public void inserisciPezzo() { // Inserisce un nuovo pezzo per l'animazione
  if (contatorePezzo == 1 || contatorePezzo == 2) { // I primi due pezzi sono a I
   griglia[0][1].setBackground(Color.RED); // Colora di rosso le caselle della griglia corrispondenti al pezzo appena introdotto
   griglia[0][2].setBackground(Color.RED);
   griglia[0][3].setBackground(Color.RED);
   griglia[0][4].setBackground(Color.RED);
   tipoPezzo = 1;
   orientamento = 'e';
   pezzo = new Pezzo(tipoPezzo); // Crea l'oggetto pezzo: tuttavia le sue coordinate saranno errate in quanto pensate per il gioco, non il menu
   pezzo.coordinateRotazione(0, 1, 0, 2, 0, 3, 0, 4); // Cambia le coordinate in quanto il costrutture inizializza le coordinate per la griglia di gioco, non quella del menu...
   pivotX = 0;
   pivotY = 2;
   if (contatorePezzo == 1) { // Avvia il timer dell'animazione con il primo pezzo
    timer = new java.util.Timer();
    timer.schedule(new scorriPezzo(), 400, 350); } }
  
  else if (contatorePezzo == 3) {
   griglia[1][6].setBackground(Color.RED);
   griglia[1][7].setBackground(Color.RED);
   griglia[1][8].setBackground(Color.RED);
   griglia[0][6].setBackground(Color.RED);
   tipoPezzo = 6;
   orientamento = 'e';
   pezzo = new Pezzo(tipoPezzo);
   pezzo.coordinateRotazione(1, 6, 1, 7, 1, 8, 0, 6);
   centroX = 1;
   centroY = 7; }
  
  else if (contatorePezzo == 4) {
   griglia[0][6].setBackground(Color.RED);
   griglia[0][7].setBackground(Color.RED);
   griglia[0][8].setBackground(Color.RED);
   griglia[0][9].setBackground(Color.RED);
   tipoPezzo = 1;
   orientamento = 'e';
   pezzo = new Pezzo(tipoPezzo);
   pezzo.coordinateRotazione(0, 6, 0, 7, 0, 8, 0, 9);
   pivotX = 0;
   pivotY = 7; }
  
  else if (contatorePezzo == 5) {
   griglia[0][8].setBackground(Color.RED);
   griglia[1][6].setBackground(Color.RED);
   griglia[1][7].setBackground(Color.RED);
   griglia[1][8].setBackground(Color.RED);
   tipoPezzo = 5;
   orientamento = 'e';
   pezzo = new Pezzo(tipoPezzo);
   pezzo.coordinateRotazione(0, 8, 1, 6, 1, 7, 1, 8);
   centroX = 1;
   centroY = 7; }
  
  else if (contatorePezzo == 6 || contatorePezzo == 7) {
   griglia[0][10].setBackground(Color.RED);
   griglia[0][11].setBackground(Color.RED);
   griglia[0][12].setBackground(Color.RED);
   griglia[0][13].setBackground(Color.RED);
   tipoPezzo = 1;
   orientamento = 'e';
   pezzo = new Pezzo(tipoPezzo);
   pezzo.coordinateRotazione(0, 10, 0, 11, 0, 12, 0, 13);
   pivotX = 0;
   pivotY = 11; }
  
  else if (contatorePezzo == 8) {
   griglia[0][14].setBackground(Color.RED);
   griglia[0][15].setBackground(Color.RED);
   griglia[0][16].setBackground(Color.RED);
   griglia[0][17].setBackground(Color.RED);
   tipoPezzo = 1;
   orientamento = 'e';
   pezzo = new Pezzo(tipoPezzo);
   pezzo.coordinateRotazione(0, 14, 0, 15, 0, 16, 0, 17);
   pivotX = 0;
   pivotY = 15; }
   
  else if (contatorePezzo == 9) {
   griglia[0][15].setBackground(Color.RED);
   griglia[1][15].setBackground(Color.RED);
   griglia[1][16].setBackground(Color.RED);
   griglia[1][17].setBackground(Color.RED);
   tipoPezzo = 5;
   orientamento = 'e';
   pezzo = new Pezzo(tipoPezzo);
   pezzo.coordinateRotazione(0, 15, 1, 15, 1, 16, 1, 17);
   centroX = 1;
   centroY = 16; }
  
  else if (contatorePezzo == 10) {
   griglia[0][18].setBackground(Color.RED);
   griglia[0][19].setBackground(Color.RED);
   griglia[0][20].setBackground(Color.RED);
   griglia[0][21].setBackground(Color.RED);
   tipoPezzo = 1;
   orientamento = 'e';
   pezzo = new Pezzo(tipoPezzo);
   pezzo.coordinateRotazione(0, 18, 0, 19, 0, 20, 0, 21);
   pivotX = 0;
   pivotY = 19; }
  
  else if (contatorePezzo == 11) {
   griglia[1][21].setBackground(Color.RED);
   griglia[1][22].setBackground(Color.RED);
   griglia[1][23].setBackground(Color.RED);
   griglia[0][23].setBackground(Color.RED);
   tipoPezzo = 6;
   orientamento = 'e';
   pezzo = new Pezzo(tipoPezzo);
   pezzo.coordinateRotazione(0, 23, 1, 21, 1, 22, 1, 23);
   centroX = 1;
   centroY = 22; }
  
  else if (contatorePezzo == 12) {
   griglia[0][21].setBackground(Color.RED);
   griglia[0][22].setBackground(Color.RED);
   griglia[1][22].setBackground(Color.RED);
   griglia[1][23].setBackground(Color.RED);
   tipoPezzo = 3;
   orientamento = 'e';
   pezzo = new Pezzo(tipoPezzo);
   pezzo.coordinateRotazione(0, 21, 0, 22, 1, 22, 1, 23);
   centroX = 1;
   centroY = 22;  }
  
  else if (contatorePezzo == 13) {
   griglia[0][21].setBackground(Color.RED);
   griglia[0][22].setBackground(Color.RED);
   griglia[0][23].setBackground(Color.RED);
   griglia[0][24].setBackground(Color.RED);
   tipoPezzo = 1;
   orientamento = 'e';
   pezzo = new Pezzo(tipoPezzo);
   pezzo.coordinateRotazione(0, 21, 0, 22, 0, 23, 0, 24);
   centroX = 0;
   centroY = 22; }
   
  else { // Termina il timer con l'ultimo pezzo e ne crea un altro per disegnare la linea finale
   timer.cancel();
   timer = new java.util.Timer();
   timer.schedule(new creaLinea(), 100, 100);  
   i = 0;
   j = 25;   } }
 
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
 
 public void spostaSinistra() { // Sposta il pezzo attuale a sinistra
  if (controllaSpazioSinistra() == true) { // Controlla prima se c'e' lo spazio 
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
   pivotY = pivotY-1; } }
	
 public boolean controllaSpazioDestra() { // Come prima, ma verso destra
  Iterator it = pezzo.getElementi().iterator();
  while (it.hasNext()) {
   Coordinate correnti = (Coordinate) it.next();
   int X = correnti.getX();
   int Y = correnti.getY();
   if (Y == 9 || griglia[X][Y+1].getBackground() == Color.BLUE) {
    return false; } }
  return true; }
  
 public void spostaDestra() { // Come prima, ma verso destra
  if (controllaSpazioDestra() == true) {
   Iterator it = pezzo.getElementi().iterator();
   while (it.hasNext()) {
    Coordinate correnti = (Coordinate) it.next();
	int X = correnti.getX();
	int Y = correnti.getY();
	if (griglia[X][Y].getName() != "Flag") {
     griglia[X][Y].setBackground(Color.WHITE); }
	if (griglia[X][Y+1].getBackground() == Color.RED) {
	 griglia[X][Y+1].setName("Flag");; }
	else {
	 griglia[X][Y+1].setBackground(Color.RED); }
	correnti.setY(Y+1); }
  cancellaFlag();
  centroY = centroY+1;
  pivotY = pivotY+1; } }
 
 public boolean controllaSpazioSotto() { // Come prima, ma verso il basso
  Iterator it = pezzo.getElementi().iterator();
  while (it.hasNext()) {
   Coordinate correnti = (Coordinate) it.next();
   int X = correnti.getX();
   int Y = correnti.getY();
   if (X == 8 || griglia[X+1][Y].getBackground() == Color.BLUE) {
    return false; } }
  return true; }

 class creaLinea extends TimerTask { // Animazione della linea blu finale
  public synchronized void run() {
   griglia[1][i].setBackground(Color.BLUE);
   griglia[2][i].setBackground(Color.BLUE);
   griglia[1][j].setBackground(Color.BLUE);
   griglia[2][j].setBackground(Color.BLUE);
   if (i == 14) {
    timer.cancel(); }
   i = i+1;
   j = j-1; } }
 
 class scorriPezzo extends TimerTask { // Animazione dei pezzi che cadono
  public synchronized void run() {
   if (contatorePezzo == 1 && pivotX == 4) { // Ruota i pezzi al momento giusto
    cambiaOrientamento(); }
   else if (contatorePezzo == 5 && (centroX == 2 || centroX == 3)) {
    cambiaOrientamento(); }
   else if (contatorePezzo == 6 && pivotX == 4) {
    cambiaOrientamento(); }
   else if (contatorePezzo == 8 && pivotX == 4) {
    cambiaOrientamento(); }
   else if (contatorePezzo == 9 && (centroX == 2 || centroX == 3)) {
    cambiaOrientamento(); }
   else if (contatorePezzo == 10 && pivotX == 4) {
    cambiaOrientamento(); }
	
   if (controllaSpazioSotto() == true) { // Scorre i pezzi in basso
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
	contatorePezzo = contatorePezzo + 1; // Aggiorna il contatore e passa al pezzo successivo
	inserisciPezzo(); } } }
 
 public synchronized void cambiaOrientamento() {
  if (tipoPezzo == 1) { // I pezzi a forma di I ruotano in base all'elemento pivot
   if (orientamento == 'n') {
	 orientamento = 'e';
	 griglia[pivotX][pivotY].setBackground(Color.WHITE);
	 griglia[pivotX-1][pivotY].setBackground(Color.WHITE);
	 griglia[pivotX-3][pivotY].setBackground(Color.WHITE);
	 griglia[pivotX-2][pivotY-1].setBackground(Color.RED);
	 griglia[pivotX-2][pivotY+1].setBackground(Color.RED);
	 griglia[pivotX-2][pivotY+2].setBackground(Color.RED);
     pezzo.coordinateRotazione(pivotX-2, pivotY-1, pivotX-2, pivotY, pivotX-2, pivotY+1, pivotX-2, pivotY+2);
	 pivotX = pivotX-2; }
   else if (orientamento == 'e') {
	 orientamento = 'n';
	 griglia[pivotX][pivotY-1].setBackground(Color.WHITE);
	 griglia[pivotX][pivotY+1].setBackground(Color.WHITE);
	 griglia[pivotX][pivotY+2].setBackground(Color.WHITE);
	 griglia[pivotX-1][pivotY].setBackground(Color.RED);
	 griglia[pivotX+1][pivotY].setBackground(Color.RED);
	 griglia[pivotX+2][pivotY].setBackground(Color.RED);
	 pezzo.coordinateRotazione(pivotX+2, pivotY, pivotX, pivotY, pivotX+1, pivotY, pivotX-1, pivotY);
	 pivotX = pivotX+2; } }
  
  else if (tipoPezzo > 1) { // Tutti gli altri pezzi usano invece un metodo in comune basato sull'elemento Centro
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
	 X = X+1; } } } }