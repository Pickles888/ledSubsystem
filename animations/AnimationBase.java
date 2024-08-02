package frc.robot.subsystems.leds.animations;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Optional;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.leds.LED.ColorLevel;
import frc.robot.subsystems.leds.LEDStrip;
import frc.robot.subsystems.leds.colors.Color;
import frc.robot.subsystems.leds.colors.Gradient;

public class AnimationBase extends LEDStrip {
    private ListIterator<Color> m_fadeColors;
    
    protected AnimationBase(CANdle candle) {
        super(candle);
    }

    /**
     * Fades in and out between color and the background color
     * 
     * @param color color of leds
     * @param led led that is at the center of the pattern
     * @param width the width of the pattern
     * @param gradientWidth the width of the gradient on both sides within the pattern
     */
    protected void setPointGradient(Color color, int led, int width, int gradientWidth, ColorLevel colorLevel) {
        int solidWidth = width - (gradientWidth * 2);
        if (solidWidth < 0)
            solidWidth = 0;
        
        int halfSolidWidth = (int) Math.floor(solidWidth / 2);
        if (halfSolidWidth < 0)
            halfSolidWidth = 0;

        setColors(
            color,
            colorLevel, 
            led - halfSolidWidth, 
            led + halfSolidWidth
        );

        int halfWidth = (int) Math.floor(solidWidth / 2);
        
        setGradient(m_bgColor, color, colorLevel, led - halfWidth, (led - halfSolidWidth) - 1);
        setGradient(color, m_bgColor, colorLevel, (led + halfSolidWidth) + 1,  led - halfWidth);
    }

    protected Color[] fade(Color color1, Color color2) {
        Gradient gradient = new Gradient(color1, color2, 1, 100);

        return gradient.genGradient();
    }

    /// Fades previous to current when previous is different from current
    protected ListIterator<Color> fadeIterator(Color previous, Color current) {
        return Arrays.asList(
            fade(previous, current)
        ).listIterator();
    }
}