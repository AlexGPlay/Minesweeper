package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.LineBorder;

import Logica.Data;
import Logica.GameController;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;

public class Window extends JFrame {

	private static final long serialVersionUID = 7813054481987747538L;
	private JPanel contentPane;
	private JPanel pnScore;
	private JPanel pnGame;
	private JMenuBar menuBar;
	private JMenu mnJuego;
	private JMenu mnAyuda;
	private JMenuItem mntmNuevo;
	private JMenuItem mntmSalir;
	private JMenuItem mntmAcercaDe;
	private GameController control;
	private Nuevo nuevo;
	private JPanel pnFlags;
	private JPanel pnReset;
	private JPanel pnTime;
	private JLabel lblFlags;
	private JLabel lblTiempo;
	private JLabel lblImagen;
	private Timer timer;
	private boolean firstTouch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		control = new GameController(Data.CASILLAS_BASE, Data.CASILLAS_BASE, Data.BOMBAS_BASE);
		nuevo = new Nuevo(this);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(10, 5));
		contentPane.add(getPnScore(), BorderLayout.NORTH);
		contentPane.add(getPnGame(), BorderLayout.CENTER);
		lblTiempo.setText("0");
		firstTouch = false;

		resizeWindow();
	}

	private Timer getTimer() {
		if (timer == null) {
			timer = new Timer(1000, new UpdateTime());
		}

		return timer;
	}

	private void resizeWindow() {
		int ancho = control.getColumnas() * Data.CASILLA_SIZE;
		int alto = control.getFilas() * Data.CASILLA_SIZE;

		pnGame.removeAll();

		int espaciosAncho = 5 * 2;
		int espaciosAlto = 5 * 3;

		int panelSuperior = Data.PANELTOP_HEIGTH;

		pnGame.setSize(ancho, alto);
		pnScore.setSize(Data.PANELTOP_WIDTH, Data.PANELTOP_HEIGTH);

		pnGame.setLayout(new GridLayout(control.getFilas(), control.getColumnas(), 0, 0));

		this.setSize(ancho + espaciosAncho, alto + espaciosAlto + panelSuperior);

		updateImage();
		addPanels();
		updateFlags();
	}

	private void updateImage() {
		ImageIcon icon = null;
		String ruta;

		if (control.isBomb())
			ruta = Data.LOSE;

		else if (control.isFinished())
			ruta = Data.WIN;

		else
			ruta = Data.HAPPY;

		icon = new ImageIcon(scaleImage(40, 40, ruta));

		lblImagen.setIcon(icon);
	}

	public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
		BufferedImage bi = null;
		try {
			ImageIcon ii = new ImageIcon(filename);// path to image
			bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) bi.createGraphics();
			g2d.addRenderingHints(
					new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bi;
	}

	private void revealPanels() {
		updateImage();

		for (Component temp : pnGame.getComponents()) {
			String[] pos = temp.getName().split(":");
			int i = Integer.parseInt(pos[0]);
			int j = Integer.parseInt(pos[1]);

			if (control.isBomb() || control.isFinished())
				for (MouseListener list : temp.getMouseListeners())
					temp.removeMouseListener(list);

			if (control.getVistos()[i][j]) {
				((JPanel) temp).removeAll();
				String text = String.valueOf(control.getTablero()[i][j]);
				if (text.equals("0"))
					text = "";

				JLabel tempL = new JLabel(text, SwingConstants.CENTER);
				Color col = getColor(text);
				tempL.setForeground(col);
				tempL.setFont(new Font("Arial", Font.BOLD, 16));
				((JPanel) temp).add(tempL, BorderLayout.CENTER);
				((JPanel) temp).setBackground(Color.LIGHT_GRAY);

			}

			if (control.getBanderas()[i][j]) {
				((JPanel) temp).removeAll();
				String text = "-2";

				JLabel tempL = new JLabel(text, SwingConstants.CENTER);
				Color col = getColor(text);
				tempL.setForeground(col);
				tempL.setFont(new Font("Arial", Font.BOLD, 16));
				((JPanel) temp).add(tempL, BorderLayout.CENTER);
				((JPanel) temp).setBackground(Color.LIGHT_GRAY);
			}

			if (!control.getBanderas()[i][j] && !control.getVistos()[i][j]) {
				((JPanel) temp).removeAll();
				((JPanel) temp).setBackground(UIManager.getColor("Panel.background"));
			}

		}

		endGame();

		pnGame.revalidate();
		pnGame.repaint();
	}

	private void endGame() {

		if (control.isFinished() || control.isBomb())
			getTimer().stop();

	}

	private Color getColor(String number) {
		Color col = null;

		switch (number) {
		case "-2":
			col = Color.CYAN;
			break;

		case "-1":
			col = Color.BLACK;
			break;

		case "0":
			col = Color.BLACK;
			break;

		case "1":
			col = Color.BLUE;
			break;

		case "2":
			col = Color.GREEN;
			break;

		case "3":
			col = Color.YELLOW;
			break;

		case "4":
			col = Color.PINK;
			break;

		case "5":
			col = Color.RED;
			break;

		case "6":
			col = Color.CYAN;
			break;

		case "7":
			col = Color.ORANGE;
			break;

		case "8":
			col = Color.BLACK;
			break;

		}

		return col;
	}

	private void addPanels() {

		for (int i = 0; i < control.getFilas(); i++) {
			for (int j = 0; j < control.getColumnas(); j++) {
				JPanel temp = new JPanel();
				temp.setBorder(new LineBorder(Color.BLACK, 1));
				temp.addMouseListener(new ClickPanel(i, j));
				temp.setName(i + ":" + j);
				temp.setLayout(new BorderLayout());
				pnGame.add(temp);
			}
		}

		contentPane.revalidate();
		contentPane.repaint();
	}

	public void recreateGame(int filas, int columnas, int bombas) {
		GameController temp = new GameController(filas, columnas, bombas);
		control = temp;
		resizeWindow();
		firstTouch = false;

		lblTiempo.setText("0");
		Timer tempTim = new Timer(1000, new UpdateTime());
		timer = tempTim;
	}

	private JPanel getPnScore() {
		if (pnScore == null) {
			pnScore = new JPanel();
			pnScore.setBorder(new LineBorder(Color.GRAY, 2));
			GridBagLayout gbl_pnScore = new GridBagLayout();
			gbl_pnScore.columnWidths = new int[] { 98, 98, 98, 0 };
			gbl_pnScore.rowHeights = new int[] { 10, 0 };
			gbl_pnScore.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
			gbl_pnScore.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
			pnScore.setLayout(gbl_pnScore);
			GridBagConstraints gbc_pnFlags = new GridBagConstraints();
			gbc_pnFlags.anchor = GridBagConstraints.WEST;
			gbc_pnFlags.fill = GridBagConstraints.VERTICAL;
			gbc_pnFlags.insets = new Insets(0, 0, 0, 5);
			gbc_pnFlags.gridx = 0;
			gbc_pnFlags.gridy = 0;
			pnScore.add(getPnFlags(), gbc_pnFlags);
			GridBagConstraints gbc_pnReset = new GridBagConstraints();
			gbc_pnReset.fill = GridBagConstraints.BOTH;
			gbc_pnReset.insets = new Insets(0, 0, 0, 5);
			gbc_pnReset.gridx = 1;
			gbc_pnReset.gridy = 0;
			pnScore.add(getPnReset(), gbc_pnReset);
			GridBagConstraints gbc_pnTime = new GridBagConstraints();
			gbc_pnTime.anchor = GridBagConstraints.EAST;
			gbc_pnTime.fill = GridBagConstraints.VERTICAL;
			gbc_pnTime.gridx = 2;
			gbc_pnTime.gridy = 0;
			pnScore.add(getPnTime(), gbc_pnTime);
		}
		return pnScore;
	}

	private JPanel getPnGame() {
		if (pnGame == null) {
			pnGame = new JPanel();
			pnGame.setBorder(new LineBorder(Color.GRAY, 2));
			pnGame.setLayout(new GridLayout(1, 0, 0, 0));
		}
		return pnGame;
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnJuego());
			menuBar.add(getMnAyuda());
		}
		return menuBar;
	}

	private JMenu getMnJuego() {
		if (mnJuego == null) {
			mnJuego = new JMenu("Juego");
			mnJuego.add(getMntmNuevo());
			mnJuego.add(getMntmSalir());
		}
		return mnJuego;
	}

	private JMenu getMnAyuda() {
		if (mnAyuda == null) {
			mnAyuda = new JMenu("Ayuda");
			mnAyuda.add(getMntmAcercaDe());
		}
		return mnAyuda;
	}

	private JMenuItem getMntmNuevo() {
		if (mntmNuevo == null) {
			mntmNuevo = new JMenuItem("Nuevo");
			mntmNuevo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nuevo.View();
				}
			});
		}
		return mntmNuevo;
	}

	private JMenuItem getMntmSalir() {
		if (mntmSalir == null) {
			mntmSalir = new JMenuItem("Salir");
			mntmSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return mntmSalir;
	}

	private JMenuItem getMntmAcercaDe() {
		if (mntmAcercaDe == null) {
			mntmAcercaDe = new JMenuItem("Acerca de");
		}
		return mntmAcercaDe;
	}

	private void play(int i, int j) {
		control.play(i, j);
		revealPanels();
		updateFlags();
	}

	private void bandera(int i, int j) {
		control.placeFlag(i, j);
		revealPanels();
		updateFlags();
	}

	private class ClickPanel extends MouseAdapter {
		int i;
		int j;

		public ClickPanel(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!firstTouch) {
				firstTouch = true;
				getTimer().start();
			}

			if (SwingUtilities.isRightMouseButton(e))
				bandera(i, j);

			else if (SwingUtilities.isLeftMouseButton(e))
				play(i, j);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			clickIn(true);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			clickIn(false);
		}

	}

	private void clickIn(boolean click) {
		ImageIcon icon = null;
		String ruta;

		if (click)
			ruta = Data.CLICK;

		else
			ruta = Data.HAPPY;

		icon = new ImageIcon(scaleImage(40, 40, ruta));

		lblImagen.setIcon(icon);
	}

	private JPanel getPnFlags() {
		if (pnFlags == null) {
			pnFlags = new JPanel();
			pnFlags.add(getLblFlags());
		}
		return pnFlags;
	}

	private JPanel getPnReset() {
		if (pnReset == null) {
			pnReset = new JPanel();
			pnReset.add(getLblImagen());
		}
		return pnReset;
	}

	private JPanel getPnTime() {
		if (pnTime == null) {
			pnTime = new JPanel();
			pnTime.add(getLblTiempo());
		}
		return pnTime;
	}

	private JLabel getLblFlags() {
		if (lblFlags == null) {
			lblFlags = new JLabel("New label");
		}
		return lblFlags;
	}

	private JLabel getLblTiempo() {
		if (lblTiempo == null) {
			lblTiempo = new JLabel("New label");
		}
		return lblTiempo;
	}

	private void updateFlags() {
		lblFlags.setText(String.valueOf(control.getBombas() - control.getCurrentFlags()));
	}

	private JLabel getLblImagen() {
		if (lblImagen == null) {
			lblImagen = new JLabel("");
			lblImagen.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					recreateGame(control.getFilas(), control.getColumnas(), control.getBombas());
				}
			});
		}
		return lblImagen;
	}

	private class UpdateTime implements ActionListener {
		int tiempo;

		public UpdateTime() {
			tiempo = 0;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			tiempo++;
			lblTiempo.setText(String.valueOf(tiempo));
		}

	}

}
