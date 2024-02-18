package com.mygdx.engine.collision;

import java.util.ArrayList;
import com.mygdx.engine.entity.Entity;

public interface iCollide {
    boolean isReachEnd(ArrayList<Entity> allEntities);
}
