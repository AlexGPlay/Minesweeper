package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logica.Data;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;

public class Nuevo extends JDialog {

	private static final long serialVersionUID = 3750101931130870646L;
	private final JPanel contentPanel = new JPanel();
	private Window parent;
	private int filas;
	private int columnas;
	private int bombas;
	private JTextField txtFilas;
	private JTextField txtColumnas;
	private JTextField txtBombas;
	private int last;
	private int carga;
	private JRadioButton rdbtnFacil;
	private JRadioButton rdbtnMedio;
	private JRadioButton rdbtnDifcil;
	private JRadioButton rdbtnPersonalizado;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public Nuevo(Window w) {
		setResizable(false);
		parent = w;
		last = 0;

		filas = Data.CASILLAS_BASE;
		columnas = Data.CASILLAS_BASE;
		bombas = Data.BOMBAS_BASE;

		setBounds(100, 100, 370, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel pnDificultad = new JPanel();
			contentPanel.add(pnDificultad, BorderLayout.WEST);
			GridBagLayout gbl_pnDificultad = new GridBagLayout();
			gbl_pnDificultad.columnWidths = new int[] { 97, 0 };
			gbl_pnDificultad.rowHeights = new int[] { 23, 0, 0, 0, 0 };
			gbl_pnDificultad.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
			gbl_pnDificultad.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
			pnDificultad.setLayout(gbl_pnDificultad);
			{
				rdbtnFacil = new JRadioButton("Fácil");
				buttonGroup.add(rdbtnFacil);
				rdbtnFacil.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						last = 0;
						datosDificultad();
					}
				});
				rdbtnFacil.setSelected(true);
				GridBagConstraints gbc_rdbtnFacil = new GridBagConstraints();
				gbc_rdbtnFacil.anchor = GridBagConstraints.WEST;
				gbc_rdbtnFacil.insets = new Insets(0, 0, 5, 0);
				gbc_rdbtnFacil.gridx = 0;
				gbc_rdbtnFacil.gridy = 0;
				pnDificultad.add(rdbtnFacil, gbc_rdbtnFacil);
			}
			{
				rdbtnMedio = new JRadioButton("Medio");
				rdbtnMedio.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						last = 1;
						datosDificultad();
					}
				});
				buttonGroup.add(rdbtnMedio);
				GridBagConstraints gbc_rdbtnMedio = new GridBagConstraints();
				gbc_rdbtnMedio.anchor = GridBagConstraints.WEST;
				gbc_rdbtnMedio.insets = new Insets(0, 0, 5, 0);
				gbc_rdbtnMedio.gridx = 0;
				gbc_rdbtnMedio.gridy = 1;
				pnDificultad.add(rdbtnMedio, gbc_rdbtnMedio);
			}
			{
				rdbtnDifcil = new JRadioButton("Difícil");
				rdbtnDifcil.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						last = 2;
						datosDificultad();
					}
				});
				buttonGroup.add(rdbtnDifcil);
				GridBagConstraints gbc_rdbtnDifcil = new GridBagConstraints();
				gbc_rdbtnDifcil.anchor = GridBagConstraints.WEST;
				gbc_rdbtnDifcil.insets = new Insets(0, 0, 5, 0);
				gbc_rdbtnDifcil.gridx = 0;
				gbc_rdbtnDifcil.gridy = 2;
				pnDificultad.add(rdbtnDifcil, gbc_rdbtnDifcil);
			}
			{
				rdbtnPersonalizado = new JRadioButton("Personalizado");
				rdbtnPersonalizado.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						last = 3;
						datosDificultad();
					}
				});
				buttonGroup.add(rdbtnPersonalizado);
				GridBagConstraints gbc_rdbtnPersonalizado = new GridBagConstraints();
				gbc_rdbtnPersonalizado.anchor = GridBagConstraints.WEST;
				gbc_rdbtnPersonalizado.gridx = 0;
				gbc_rdbtnPersonalizado.gridy = 3;
				pnDificultad.add(rdbtnPersonalizado, gbc_rdbtnPersonalizado);
			}
		}
		{
			JPanel pnDatos = new JPanel();
			contentPanel.add(pnDatos, BorderLayout.CENTER);
			GridBagLayout gbl_pnDatos = new GridBagLayout();
			gbl_pnDatos.columnWidths = new int[] { 0, 0, 0 };
			gbl_pnDatos.rowHeights = new int[] { 0, 0, 0, 0 };
			gbl_pnDatos.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
			gbl_pnDatos.rowWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
			pnDatos.setLayout(gbl_pnDatos);
			{
				JLabel lblFilas = new JLabel("Filas:");
				GridBagConstraints gbc_lblFilas = new GridBagConstraints();
				gbc_lblFilas.anchor = GridBagConstraints.SOUTHEAST;
				gbc_lblFilas.insets = new Insets(0, 0, 5, 5);
				gbc_lblFilas.gridx = 0;
				gbc_lblFilas.gridy = 0;
				pnDatos.add(lblFilas, gbc_lblFilas);
			}
			{
				txtFilas = new JTextField();
				GridBagConstraints gbc_txtFilas = new GridBagConstraints();
				gbc_txtFilas.anchor = GridBagConstraints.SOUTHWEST;
				gbc_txtFilas.insets = new Insets(0, 0, 5, 0);
				gbc_txtFilas.gridx = 1;
				gbc_txtFilas.gridy = 0;
				pnDatos.add(txtFilas, gbc_txtFilas);
				txtFilas.setColumns(10);
			}
			{
				JLabel lblColumnas = new JLabel("Columnas:");
				GridBagConstraints gbc_lblColumnas = new GridBagConstraints();
				gbc_lblColumnas.anchor = GridBagConstraints.EAST;
				gbc_lblColumnas.insets = new Insets(0, 0, 5, 5);
				gbc_lblColumnas.gridx = 0;
				gbc_lblColumnas.gridy = 1;
				pnDatos.add(lblColumnas, gbc_lblColumnas);
			}
			{
				txtColumnas = new JTextField();
				GridBagConstraints gbc_txtColumnas = new GridBagConstraints();
				gbc_txtColumnas.anchor = GridBagConstraints.WEST;
				gbc_txtColumnas.insets = new Insets(0, 0, 5, 0);
				gbc_txtColumnas.gridx = 1;
				gbc_txtColumnas.gridy = 1;
				pnDatos.add(txtColumnas, gbc_txtColumnas);
				txtColumnas.setColumns(10);
			}
			{
				JLabel lblBombas = new JLabel("Bombas:");
				GridBagConstraints gbc_lblBombas = new GridBagConstraints();
				gbc_lblBombas.anchor = GridBagConstraints.NORTHEAST;
				gbc_lblBombas.insets = new Insets(0, 0, 0, 5);
				gbc_lblBombas.gridx = 0;
				gbc_lblBombas.gridy = 2;
				pnDatos.add(lblBombas, gbc_lblBombas);
			}
			{
				txtBombas = new JTextField();
				GridBagConstraints gbc_txtBombas = new GridBagConstraints();
				gbc_txtBombas.anchor = GridBagConstraints.NORTHWEST;
				gbc_txtBombas.gridx = 1;
				gbc_txtBombas.gridy = 2;
				pnDatos.add(txtBombas, gbc_txtBombas);
				txtBombas.setColumns(10);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveData();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						visibleOff();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		datosDificultad();
	}

	public void visibleOff() {
		this.setVisible(false);
	}

	public void saveData() {
		filas = Integer.parseInt(txtFilas.getText());
		columnas = Integer.parseInt(txtColumnas.getText());
		bombas = Integer.parseInt(txtBombas.getText());
		carga = last;

		parent.recreateGame(filas, columnas, bombas);
		this.setVisible(false);
		this.setModal(false);
	}

	public void View() {
		txtFilas.setText(String.valueOf(filas));
		txtColumnas.setText(String.valueOf(columnas));
		txtBombas.setText(String.valueOf(bombas));

		switch (carga) {
		case 0:
			rdbtnFacil.setSelected(true);
			break;

		case 1:
			rdbtnMedio.setSelected(true);
			break;

		case 2:
			rdbtnDifcil.setSelected(true);
			break;

		case 3:
			rdbtnPersonalizado.setSelected(true);
			break;
		}

		this.setVisible(true);
		this.setModal(true);
	}

	public void datosDificultad() {

		switch (last) {
		case 0:
			txtFilas.setEnabled(false);
			txtColumnas.setEnabled(false);
			txtBombas.setEnabled(false);

			txtFilas.setText(String.valueOf(Data.CASILLAS_BASE));
			txtColumnas.setText(String.valueOf(Data.CASILLAS_BASE));
			txtBombas.setText(String.valueOf(Data.BOMBAS_BASE));
			break;

		case 1:
			txtFilas.setEnabled(false);
			txtColumnas.setEnabled(false);
			txtBombas.setEnabled(false);

			txtFilas.setText(String.valueOf(Data.CASILLAS_MEDIO));
			txtColumnas.setText(String.valueOf(Data.CASILLAS_MEDIO));
			txtBombas.setText(String.valueOf(Data.BOMBAS_MEDIO));
			break;

		case 2:
			txtFilas.setEnabled(false);
			txtColumnas.setEnabled(false);
			txtBombas.setEnabled(false);

			txtFilas.setText(String.valueOf(Data.CASILLAS_DIFICIL));
			txtColumnas.setText(String.valueOf(Data.CASILLAS_DIFICIL));
			txtBombas.setText(String.valueOf(Data.BOMBAS_DIFICIL));
			break;

		case 3:
			txtFilas.setEnabled(true);
			txtColumnas.setEnabled(true);
			txtBombas.setEnabled(true);
			break;
		}

	}

}
