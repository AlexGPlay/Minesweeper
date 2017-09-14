package Logica;

public class GameController {

	private int filas;
	private int columnas;
	private int bombas;
	private int maxFlags;
	private int[][] tablero;
	private boolean[][] vistos;
	private boolean[][] bandera;
	private boolean bomb;
	private boolean victoria;

	public GameController(int filas, int columnas, int bombas) {
		this.filas = filas;
		this.columnas = columnas;
		this.bombas = bombas;

		tablero = new int[filas][columnas];
		vistos = new boolean[filas][columnas];
		bandera = new boolean[filas][columnas];
		generateGame();
		
		bomb = false;
		victoria = false;
		maxFlags = 0;
	}
	
	public int getCurrentFlags() {
		return maxFlags;
	}
	
	public boolean isBomb() {
		return bomb;
	}

	public boolean[][] getVistos() {
		return vistos;
	}

	public void play(int i, int j) {
		if(bandera[i][j])
			maxFlags--;
		
		vistos[i][j] = true;
		bandera[i][j] = false;
		
		if(tablero[i][j]==-1) {
			bomb = true;
			revealBombs();
		}

		else if(tablero[i][j]==0) {
			playRec(i,j+1);
			playRec(i,j-1);
			playRec(i+1,j);
			playRec(i-1,j);
		}
		
		checkVictory();
	}
	
	public void revealBombs() {
		
		for(int i=0;i<filas;i++) 
			for(int j=0;j<columnas;j++) 
				if(tablero[i][j]==-1)
					vistos[i][j] = true;
			
	}
	
	public boolean[][] getBanderas(){
		return bandera;
	}
	
	public void placeFlag(int i, int j) {
		if(!bandera[i][j] && maxFlags<bombas) {
			bandera[i][j] = true;
			maxFlags++;
		}
		
		else if(bandera[i][j]){
			bandera[i][j] = false;
			maxFlags--;
		}
		
		checkVictory();
	}
	
	public void checkVictory() {
		int banderasCorrectas = 0;
		int huecosTotales = 0;
		
		for(int i=0;i<filas;i++) {
			for(int j=0;j<columnas;j++) {
				if(!vistos[i][j])
					huecosTotales++;
				
				if(bandera[i][j] && tablero[i][j]==-1)
					banderasCorrectas++;
			}
		}
		
		if(huecosTotales==bombas || banderasCorrectas==bombas)
			victoria = true;
	}

	private void playRec(int i, int j) {
		if(i<0 || j<0 || j>=columnas || i>=filas || vistos[i][j]==true)
			return;

		vistos[i][j] = true;
		
		if(bandera[i][j])
			maxFlags--;
		
		bandera[i][j] = false;

		if(tablero[i][j]==0) {
			playRec(i,j+1);
			playRec(i,j-1);
			playRec(i+1,j);
			playRec(i-1,j);
		}
	}

	public int[][] getTablero(){
		return tablero;
	}

	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}

	public int getBombas() {
		return bombas;
	}

	private void generateGame() {

		int f = 0;
		int c = 0;
		int nBombas = 0;

		while(nBombas < bombas) {
			do {
				f = (int)(Math.random()*filas);
				c = (int)(Math.random()*columnas);
			}while(tablero[f][c]==-1);

			tablero[f][c] = -1;
			nBombas++;
		}

		fillGame();
	}

	private void fillGame() {

		for(int i=0;i<filas;i++) {
			for(int j=0;j<columnas;j++) {
				tablero[i][j] = checkBombs(i,j);
				vistos[i][j] = false;
				bandera[i][j] = false;
			}
		}

	}
	
	public boolean isFinished() {
		return victoria;
	}

	private int checkBombs(int f, int c) {
		if(tablero[f][c]==-1)
			return -1;

		int bombs = 0;
		int[] xMoves = {-1,0,1};
		int[] yMoves = {-1,0,1};

		for(int i=0;i<xMoves.length;i++) {
			for(int j=0;j<yMoves.length;j++) {
				int nF = f+xMoves[i];
				int nC = c+yMoves[j];

				if(!(nF==f && nC==c) && nF>-1 && nC>-1 && nF<filas && nC<columnas) {
					if(tablero[nF][nC]==-1)
						bombs++;
				}	

			}
		}

		return bombs;
	}

	public String toString() {
		String s = "";

		for(int i=0;i<filas;i++) {
			for(int j=0;j<columnas;j++) {
				if(tablero[i][j]==-1)
					s += "B" + " ";

				else
					s += tablero[i][j] + " ";
			}
			s += "\n";
		}

		return s;
	}

}
