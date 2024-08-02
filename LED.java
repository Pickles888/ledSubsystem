package frc.robot.subsystems.leds;

import java.util.Optional;

import frc.robot.subsystems.leds.colors.Color;

public class LED {
    private Optional<Color> m_fgColor = Optional.empty();
    private Optional<Color> m_mgColor = Optional.empty();
    private Optional<Color> m_bgColor = Optional.empty();

    public enum ColorLevel {
        FOREGROUND,
        MIDGROUND,
        BACKGOUND,
    }

    public LED(Color color, ColorLevel colorLevel) {
        switch (colorLevel) {
            case FOREGROUND:
                m_fgColor = Optional.of(color);
                break;
            case MIDGROUND:
                m_mgColor = Optional.of(color);
                break;
            case BACKGOUND:
                m_bgColor = Optional.of(color);
                break;
            default:
                System.err.println("Invalid enum for `LED` constructor, defaulting to MIDGROUND");
                m_mgColor = Optional.of(color);
                break;
        }
    }

    public LED(Color color) {
        m_fgColor = Optional.of(color);
        m_mgColor = Optional.of(color);
        m_bgColor = Optional.of(color);
    }

    public Color getColor() { 
        if (m_fgColor.isPresent()) return m_fgColor.get();
        if (m_mgColor.isPresent()) return m_mgColor.get();
        if (m_bgColor.isPresent()) return m_bgColor.get();
        System.err.println("Requested color does not exist");
        return new Color(0, 0, 0);
    };

    public void setColor(ColorLevel colorLevel, Color color) {
        switch (colorLevel) {
            case FOREGROUND:
                m_fgColor = Optional.of(color);
                break;
            case MIDGROUND:
                m_mgColor = Optional.of(color);
                break;
            case BACKGOUND:
                m_bgColor = Optional.of(color);
                break;
            default:
                System.err.println("Invalid enum for `setColor()` method, defaulting to MIDGROUND");
                m_mgColor = Optional.of(color);
                break;
        }
    };

    public void removeColor(ColorLevel colorLevel) {
        switch (colorLevel) {
            case FOREGROUND:
                m_fgColor = Optional.empty();
                break;
            case MIDGROUND:
                m_mgColor = Optional.empty();
                break;
            case BACKGOUND:
                m_bgColor = Optional.empty();
                break;
            default:
                System.err.println("Invalid enum for `setColor()` method, not removing");
                break;
        }
    };

    public void reset() {
        m_fgColor = Optional.empty();
        m_mgColor = Optional.empty();
        m_bgColor = Optional.empty();
    }
}
