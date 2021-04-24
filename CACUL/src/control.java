
import java.math.BigDecimal;
import java.math.MathContext;
import javax.swing.JButton;
import javax.swing.JTextField;

public class control {

    private BigDecimal firstNum;
    private BigDecimal secondNum;
    private JTextField text;
    private boolean process;
    private boolean reset;
    private int operator =-1;
    private BigDecimal memory=new BigDecimal("0");

    public control(JTextField text) {
        this.text=text;
        operator=-1;
    }
    
    public void pressNumber(JButton button){    
        String a=button.getText();
        BigDecimal b;
        if(process|| reset){
            text.setText("0");
            process=false;
            reset=false;
        }
        b=new BigDecimal(text.getText()+a);
        text.setText(b+"");
        
    }
    
    public void setOperator(int operator){
        this.operator=operator;
    }
    
    public void pressDot(){
        if(!text.getText().contains(".")){
            text.setText(text.getText()+".");
        }
    }
    public void Clear(){
        firstNum=new BigDecimal("0");
        secondNum=new BigDecimal("0");
        operator=-1;
    }
    public BigDecimal getValue(){
        
        String value=text.getText();
        BigDecimal bd;
        try {
            bd=new BigDecimal(value);
        } catch (Exception e) {
            return  firstNum;
            
        }
        return  bd;
    }
    public void caculate(){
        if(!process){
            if(operator==-1){
                firstNum=getValue();
            }
            else{
                secondNum=getValue();
                switch(operator){
                    case  1:
                        firstNum=firstNum.add(secondNum);
                        break;
                    case  2:
                        firstNum=firstNum.subtract(secondNum);
                        break;
                    case  3:
                        firstNum=firstNum.multiply(secondNum);
                        break;
                    case  4:
                         if(secondNum.toString().equals("0")){
                             text.setText("ERROR");
                             process=false;
                             return;
                         }else{
                             firstNum=firstNum.divide(secondNum, new MathContext(20));
                              break;
                         }
                }
            }
            text.setText(firstNum+"");
            process=true;
            
        }
    }
    
    public void pressResult(){
     if(!text.getText().equals("ERROR")){
         caculate();
         operator=-1;
     }else{
         text.setText(firstNum+"");
     }
    }
    public void pressNegate(){
        pressResult();
        text.setText(getValue().negate()+"");
        process=false;
        reset=true;
    }

    public void pressSqrt(){
        pressResult();
        BigDecimal bd=getValue();
        if(bd.doubleValue()>=0){
            String value=Math.sqrt(bd.doubleValue())+"";
            if(value.endsWith(".0")){
                value=value.replace(".0", "");
                
            }
            text.setText(value);
            process=false;
            
        }
        else{
            text.setText("ERROR");
        }
        reset=true;
    }
    
    public void pressPercent(){
        text.setText(getValue().doubleValue()/100+"");
        process=false;
        reset=true;
    }
    public void pressInvert(){
        pressResult();
        double value=getValue().doubleValue();
        if(value!=0){
            text.setText(1/value+"");
            process=false;
            
        }else{
            text.setText("ERROR");
        }
        reset=true;
    }
    public void pressMR(){
        text.setText(memory+"");
        reset=true;
        process=false;
    }
    public void pressMC(){
        memory=new BigDecimal("0");
    }
    public void pressMAdd(){
        memory=memory.add(getValue());
        reset=true;
        process=false;
    }
    public void pressMSub(){
        memory=memory.add(getValue().negate());
        reset=true;
        process=false;
    }
}
