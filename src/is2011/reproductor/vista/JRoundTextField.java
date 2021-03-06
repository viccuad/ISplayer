package is2011.reproductor.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder; 


@SuppressWarnings("serial")
public class JRoundTextField extends JTextField { 

	/** Alineamiento a la izquierda */
    private Boolean left = true;
    
    /** Grosor del borde */
    protected float anchoDeBorde = 2f;
    
    /** Color del borde */
    protected Color colorDeBorde = new Color(173,173,173);
    
    /** Texto que se muestra */
    private String descripcion = "Buscar...";
    
    /** Color del texto que se muestra */
    private Color colorDeTextoBackground = Color.GRAY;
    
    /** Indica el angulo de corvatura de las esquinas */
    private Integer curvatura = 20;

    
    /**
     * Constructor por defecto
     */
    public JRoundTextField() {
    	super(null,0);
        setOpaque(false);
        setBorder(new EmptyBorder(0,5,0,2));
        setPreferredSize(new Dimension(200, 23));
        setFont(new Font("", Font.ITALIC , 12));
    }

    
    /**
     * Crea un objeto con el texto y número de 
     * columnas que recibe como parámetro
     * @param texto texto inicial a mostrar
     * @param col numero de columnas
     */
    public JRoundTextField(String texto, int col) {
    	super(null,col); 
        setOpaque(false);
        setBorder(new EmptyBorder(0,5,0,2));
        setPreferredSize(new Dimension(200, 23));
        descripcion = texto;
        setFont(new Font("", Font.ITALIC , 12));
    }
    
    
    /**
     * Método que es invocado cuándo se va a dibujar el componente
     */
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        Paint oldPaint = g2.getPaint();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        		RenderingHints.VALUE_ANTIALIAS_ON);
        
        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0,0,getWidth(),
        		getHeight(), curvatura, curvatura);
        g2.clip(r2d);

        g2.setColor(getBackground());
        g2.fillRoundRect(0,0,getWidth(),getHeight(),curvatura,curvatura);

        
        if(getText().length()<1){
            Insets insets = getInsets();
            FontMetrics fm = getFontMetrics(getFont());
            g2.setColor(getForeground());
            
            if(getText()==null)
                setText("  ");
            
            TextLayout layout = new TextLayout(descripcion == null ? " ":descripcion,
                    					getFont(), g2.getFontRenderContext());
            
            Rectangle2D bounds = layout.getBounds();

            int x = (int) (getWidth() - insets.left - insets.right - 
            		bounds.getWidth()) / 2;
            
            if(getHorizontalAlignment()!=CENTER)
                x = 0+insets.left;
            
            int y = (getHeight() - insets.top - insets.bottom - 
            		fm.getMaxAscent() - fm.getMaxDescent()) / 2;
            y += fm.getAscent() - 1;
            g2.setColor(colorDeTextoBackground);
            layout.draw(g2, x, y);
        }
        
        g2.setPaint(oldPaint);
        super.paintComponent(g);
    }

    

    @Override
    protected void paintBorder(Graphics g) {
        int x = 1, y = 1;
        int w = getWidth() - 2, h = getHeight() - 2;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        		RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(anchoDeBorde));
        g2.setColor(colorDeBorde);
        g2.drawRoundRect(x, y, w, h, curvatura, curvatura);
        g2.dispose();
    }
    
    

    public Color getColorDeTextoBackground() {
        return colorDeTextoBackground;
    }

    public void setColorDeTextoBackground(Color colorDeTextoBackground) {
        this.colorDeTextoBackground = colorDeTextoBackground;
    }
    
    public Boolean getLeft() {
        return left;
    }

    public void setLeft(Boolean left) {
        this.left = left;
        repaint();
    }

    public float getAnchoDeBorde() {
        return anchoDeBorde;
    }

    public void setAnchoDeBorde(float anchoDeBorde) {
        this.anchoDeBorde = anchoDeBorde;
    }

    public Color getColorDeBorde() {
        return colorDeBorde;
    }

    public void setColorDeBorde(Color colorDeBorde) {
        this.colorDeBorde = colorDeBorde;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    

}