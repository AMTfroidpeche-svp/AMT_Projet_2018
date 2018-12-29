package ch.heig.gamification.api.spec.steps;

/**
 * File : Environment.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : This Class stores the correct states of the database if everything went well
 *
 */

import java.util.ArrayList;
import java.util.List;
import ch.heig.gamification.api.dto.Badge;
import ch.heig.gamification.api.dto.PointScale;
import ch.heig.gamification.api.dto.Rule;

public final class CurrentState {
   public static List<Badge> badges = new ArrayList<>();
   public static List<PointScale> pointScales = new ArrayList<>();
   public static List<Rule> rules = new ArrayList<>();

   private CurrentState(){}

}
