package reversi;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

	
	public class ListenerCase implements MouseListener{
	    
	    private Case case1;
	    private Plateau plateau;

	    
	    public ListenerCase(Case case1, Plateau plateau) {
	        super();
	        this.case1 = case1;
	        this.plateau = plateau;
	    }


	    public void mouseClicked(MouseEvent arg0) {
	    
	    }


                public void mouseEntered(MouseEvent arg0) {
	       /*if(case1.getComponentCount()==1){
                ((Pion)(case1.getComponent(0))).grise();
                case1.validate();
                case1.repaint();
                }
               */
               plateau.griser(case1);
               
	    }


	    public void mouseExited(MouseEvent arg0) {
	       /*if(case1.getComponentCount()==1){
                ((Pion)(case1.getComponent(0))).degrise();
                case1.validate();
                case1.repaint();
               }*/
                plateau.degrise();
	    }


	    public void mousePressed(MouseEvent arg0) {
	    	plateau.poserPion(case1);    
                
            }
	 


	    public void mouseReleased(MouseEvent arg0) {
	        
	    }
}

