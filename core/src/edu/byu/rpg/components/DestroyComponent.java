package edu.byu.rpg.components;

import com.badlogic.ashley.core.Component;

/**
 * This component flags an entity for removal from {@link edu.byu.rpg.RpgGame#engine} at the end of the next update loop,
 * essentially destroying it at the end of that update loop.
 */
public class DestroyComponent implements Component {}
