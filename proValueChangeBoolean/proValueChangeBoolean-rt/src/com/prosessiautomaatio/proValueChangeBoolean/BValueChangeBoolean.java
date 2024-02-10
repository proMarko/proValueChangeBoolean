package com.prosessiautomaatio.proValueChangeBoolean;


import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.sys.*;
import java.util.Timer;
import java.util.TimerTask;


@NiagaraProperty(
        name = "delay",
        type = "baja:Integer",
        defaultValue= "BInteger.make(0)"
)

@NiagaraProperty(
        name = "in",
        type = "baja:Double",
        defaultValue= "BDouble.make(0)",
        flags = Flags.SUMMARY
)

@NiagaraProperty(
        name = "out",
        type = "baja:Boolean",
        defaultValue= "BBoolean.make(false)",
        flags = Flags.SUMMARY
)

public class BValueChangeBoolean extends BComponent {
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.prosessiautomaatio.proValueChangeBoolean.BValueChangeBoolean(2223976855)1.0$ @*/
/* Generated Thu Jan 18 18:26:31 EET 2024 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "delay"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code delay} property.
   * @see #getDelay
   * @see #setDelay
   */
  public static final Property delay = newProperty(0, ((BInteger)(BInteger.make(0))).getInt(), null);
  
  /**
   * Get the {@code delay} property.
   * @see #delay
   */
  public int getDelay() { return getInt(delay); }
  
  /**
   * Set the {@code delay} property.
   * @see #delay
   */
  public void setDelay(int v) { setInt(delay, v, null); }

////////////////////////////////////////////////////////////////
// Property "in"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.SUMMARY, ((BDouble)(BDouble.make(0))).getDouble(), null);
  
  /**
   * Get the {@code in} property.
   * @see #in
   */
  public double getIn() { return getDouble(in); }
  
  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(double v) { setDouble(in, v, null); }

////////////////////////////////////////////////////////////////
// Property "out"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.SUMMARY, ((BBoolean)(BBoolean.make(false))).getBoolean(), null);
  
  /**
   * Get the {@code out} property.
   * @see #out
   */
  public boolean getOut() { return getBoolean(out); }
  
  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(boolean v) { setBoolean(out, v, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BValueChangeBoolean.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    public void started() throws Exception
    {
        ValueChange();

        this.WatchDog  = new Thread(() -> {
            watchDog();
        });

        WatchDog.start();
    }

    public void stopped() throws Exception
    {
        cancelTimer();
        stop = true;
    }

    boolean stop = false;
    Thread WatchDog;
    int period = 1;
    double lastIn = 0;
    double lastPeriod;
    Timer timer = new Timer();
    long timeMillis = System.currentTimeMillis();
    boolean timerRunning = false;
    boolean timerEnd = false;

    private void watchDog(){

        lastPeriod = period;

        while(!stop){

            try{

                if(lastPeriod != getDelay()){
                    cancelTimer();
                    lastPeriod = getDelay();
                }

                Thread.sleep(1000);
                ValueChange();

            }catch (Exception ex){

                System.out.println(ex.getMessage());
            }
        }


    }


    public void ValueChange() {

        if(!timerRunning){

            timerRunning = true;

            if(getDelay() > 0){
                period = getDelay();
            }else {
                period = 1;
            }

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override

                public void run() {
                    timerEnd = true;

                    try{


                        double in = getIn();
                        if (!Double.isNaN(in)) {
                                if(in != lastIn){
                                lastIn = in;
                                timeMillis = System.currentTimeMillis();
                                setOut(true);

                            }
                        }

                        if (System.currentTimeMillis() - timeMillis > getDelay() * 1000) {
                            setOut(false);
                        }

                    }catch(Exception e){

                    }
                    timerEnd = false;
                }
            }, 0, 10L);

        }
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = new Timer();

            try{
                while (timerEnd){
                    Thread.sleep(100);
                }
            }catch (Exception ex){

            }
        }

        timerRunning = false;
    }

    @Override
    public BIcon getIcon() { return icon; }
    private static final BIcon icon = BIcon.make("local:|module://proValueChangeBoolean/icons/boolean.png");


}
