package reversi;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;

import javax.swing.JPanel;


	public class Case extends JPanel {

	    public Case(){
	        setLayout(new GridLayout(1,0));
	        initCouleur();
	    }


	    
	    
	    private void initCouleur(){    
	            setBackground(new Color(0,105,0));
	            setBorder(BorderFactory.createLineBorder(new Color(0,65,0)));               
	        }

	}
