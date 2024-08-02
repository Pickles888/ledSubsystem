package frc.robot.subsystems.leds;

import java.util.Optional;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Constants.LEDConstants;
import frc.robot.subsystems.leds.LED.ColorLevel;
import frc.robot.subsystems.leds.colors.Color;
import frc.robot.subsystems.leds.colors.Gradient;

public class LEDStrip {
    protected CANdle m_candle;

    protected Color m_blank = new Color(0, 0, 0);
    protected Color m_grey = new Color(128, 128, 128);
    /**
     * Using array instead of changing each LED individualy
     */
    protected LED[] m_LEDStrip = new LED[LEDConstants.kNumOfLEDs];
    protected Color m_bgColor = m_blank;

    protected LEDStrip(CANdle candle) {
        m_candle = candle;

        for (int i = 0; i < LEDConstants.kNumOfLEDs; i++)
            m_LEDStrip[i] = new LED(m_bgColor);
    }

    protected LEDStrip(CANdle candle, Color bgColor) {
        m_candle = candle;
        m_bgColor = bgColor;

        for (int i = 0; i < LEDConstants.kNumOfLEDs; i++)
            m_LEDStrip[i] = new LED(m_bgColor);
    }

    public void updateLEDs() {
        for (int i = 0; i < LEDConstants.kNumOfLEDs; i++) {
            Color color = m_LEDStrip[i].getColor();
            m_candle.setLEDs(color.getR(), color.getG(), color.getB());
        };
    }

    protected Color avarageColors(Color color1, Color color2) {
        double h = (color1.getH() + color2.getH()) / 2;
        double s = (color1.getS() + color2.getS()) / 2;
        double v = (color1.getV() + color2.getV()) / 2;

        return new Color(h, s ,v);
    }

    protected void setColors(Color color, ColorLevel colorLevel,int from, int to) {
        for (int i = from; i <= to; i ++) 
            m_LEDStrip[i].setColor(colorLevel, color);
    }

    protected void setColorsNull(Color color, ColorLevel colorLevel,int from, int to) {
        for (int i = from; i <= to; i ++) 
            this.setColorNull(color, colorLevel, i);
    }

    protected void setColorNull(Color color, ColorLevel colorLevel, int index) {
        LED led = m_LEDStrip[index];
        
        if (led != null) {
            led.setColor(colorLevel, color);
        }
    }

    protected void setColor(Color color, ColorLevel colorLevel, int index) {
        m_LEDStrip[index].setColor(colorLevel, color);
    }

    protected void setGradient(Color Color1, Color Color2, ColorLevel colorLevel, int from, int to) {
        Gradient gradient = new Gradient(Color1, Color2, from, to);
        Color[] gradientColors = gradient.genGradient();

        int j = 0;
        for (int i = from; i <= to; i++) {
            setColor(gradientColors[j], colorLevel, i);
            j++;
        }
    }

    protected Color getAllianceColor() {
        Optional<Alliance> ally = DriverStation.getAlliance();

        if (ally.isPresent()) {
            if (ally.get() == Alliance.Red) return new Color(255, 0, 0);
            if (ally.get() == Alliance.Blue) return new Color(0, 0, 255);
        }

        return m_grey;
    }
}
