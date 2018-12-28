package ch.heig.gamification.api.spec.steps;

import java.util.ArrayList;
import java.util.List;
import ch.heig.gamification.api.dto.Badge;
import ch.heig.gamification.api.dto.PointScale;
import ch.heig.gamification.api.dto.Rule;
import ch.heig.gamification.api.dto.User;

public final class CurrentState {
   public static List<Badge> badges = new ArrayList<>();
   public static List<PointScale> pointScales = new ArrayList<>();
   public static List<Rule> rules = new ArrayList<>();
   public static User user = new User();

   private CurrentState(){}

}
