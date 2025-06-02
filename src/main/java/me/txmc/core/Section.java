package me.txmc.core;

/**
 * Minimal section interface used for modular systems in 6b6t.
 */
public interface Section {
    void enable();
    void disable();
    void reloadConfig();
    String getName();
}
