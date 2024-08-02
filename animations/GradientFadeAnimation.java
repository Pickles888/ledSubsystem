package frc.robot.subsystems.leds.animations;

import java.util.ListIterator;
import java.util.function.Supplier;

import com.ctre.phoenix.led.CANdle;

import frc.robot.Constants.LEDConstants;
import frc.robot.subsystems.leds.LED.ColorLevel;
import frc.robot.subsystems.leds.colors.Color;

public class GradientFadeAnimation extends AnimationBase {
    private Supplier<Color> m_colorSup;

    private Color m_colorSupPrev;
    private ListIterator<Color> m_stateColorIterator;
    
    public GradientFadeAnimation(CANdle candle, Supplier<Color> colorSup) {
        super(candle);

        m_colorSup = colorSup;
        setGradient(getAllianceColor(), m_colorSup.get(), ColorLevel.FOREGROUND, 0, LEDConstants.kNumOfLEDs);
    }
    
    public void update() {
        Color currentColor;
        
        if (m_colorSupPrev != m_colorSup.get()) {
            if (m_stateColorIterator.hasNext())
                m_colorSupPrev = m_stateColorIterator.next();
            m_stateColorIterator = fadeIterator(m_colorSupPrev, m_colorSup.get());
        };

        if (m_stateColorIterator.hasNext()) {
            currentColor = m_stateColorIterator.next();
        } else {
            currentColor = m_colorSup.get();
        }

        setGradient(getAllianceColor(), currentColor, ColorLevel.FOREGROUND, 0, LEDConstants.kNumOfLEDs);
        updateLEDs();

        m_colorSupPrev = m_colorSup.get();
    }
}
