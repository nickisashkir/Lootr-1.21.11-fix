package noobanidus.mods.lootr.common.mixin.hopper;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import noobanidus.mods.lootr.common.api.LootrTags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

/**
 * Previously targeted {@code lambda$static$2} by its synthetic name, which is unstable
 * across compilations and requires a refmap that wasn't being shipped. Rewritten to wrap
 * the field directly after {@code <clinit>} runs — no dependency on lambda numbering.
 */
@Mixin(EntitySelector.class)
public class MixinEntitySelector {
  @Shadow
  @Final
  @Mutable
  public static Predicate<Entity> CONTAINER_ENTITY_SELECTOR;

  @Inject(method = "<clinit>", at = @At("TAIL"))
  private static void lootr$wrapContainerEntitySelector(CallbackInfo ci) {
    Predicate<Entity> original = CONTAINER_ENTITY_SELECTOR;
    CONTAINER_ENTITY_SELECTOR = entity -> {
      if (entity.getType().is(LootrTags.Entity.CONVERT_ITEM_FRAMES)) {
        return false;
      }
      return original.test(entity);
    };
  }
}
