package me.deecaad.weaponmechanics.weapon.weaponevents;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class WeaponStopShootingEvent extends WeaponEvent {
    private static final HandlerList handlers = new HandlerList();

    private final Map<String, Long> lastShootTime = new HashMap<>();

    public WeaponStopShootingEvent(String weaponTitle, ItemStack weaponStack, LivingEntity shooter, EquipmentSlot hand, Long lastShootTime) {
        super(weaponTitle, weaponStack, shooter, hand);
        this.lastShootTime.put(weaponTitle, lastShootTime);
    }

    public long getLastShootTime() {
        return lastShootTime.getOrDefault(weaponTitle,0L);
    }

    @NotNull @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull public static HandlerList getHandlerList() {
        return handlers;
    }
}
