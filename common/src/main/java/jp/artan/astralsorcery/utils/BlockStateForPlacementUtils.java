package jp.artan.astralsorcery.utils;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.Vec3;

public class BlockStateForPlacementUtils {

    /**
     * ブロックをクリックした座標を取得
     */
    public static Point getBlockClickPoint(BlockPlaceContext pContext) {
        Direction direction = pContext.getClickedFace();
        Vec3 position = getBlockPosition(pContext);
        return switch(direction) {
            case UP -> switch(pContext.getPlayer().getDirection()) {
                case NORTH -> new Point(getFloorDot2(1 - position.x()), getFloorDot2(1 - position.z()), position.y());
                case EAST -> new Point(getFloorDot2(1 - position.z()), position.x(), position.y());
                case SOUTH -> new Point(position.x(), position.z(), position.y());
                case WEST -> new Point(position.z(), getFloorDot2(1 - position.x()), position.y());
                default -> new Point(position.x(), position.z(), position.y());
            };
            case DOWN -> switch(pContext.getPlayer().getDirection()) {
                case NORTH -> new Point(getFloorDot2(1 - position.x()), getFloorDot2(1 - position.z()), getFloorDot2(1 - position.y()));
                case EAST -> new Point(getFloorDot2(1 - position.z()), position.x(), getFloorDot2(1 - position.y()));
                case SOUTH -> new Point(position.x(), position.z(), getFloorDot2(1 - position.y()));
                case WEST -> new Point(position.z(), getFloorDot2(1 - position.x()), getFloorDot2(1 - position.y()));
                default -> new Point(position.x(), position.z(), getFloorDot2(1 - position.y()));
            };
            case NORTH -> new Point(position.x(), position.y(), getFloorDot2(1 - position.z()));
            case SOUTH -> new Point(getFloorDot2(1 - position.x()), position.y(), position.z());
            case WEST -> new Point(getFloorDot2(1 - position.z()), position.y(), getFloorDot2(1 - position.x()));
            case EAST -> new Point(position.z(), position.y(), position.x());
        };
    }

    /**
     * 選択した座標を取得。小数第3位で切り捨て。
     */
    public static Vec3 getBlockPosition(BlockPlaceContext pContext) {
        double x = getFloorDot2(pContext.getClickLocation().x - (double)pContext.getClickedPos().getX());
        double y = getFloorDot2(pContext.getClickLocation().y - (double)pContext.getClickedPos().getY());
        double z = getFloorDot2(pContext.getClickLocation().z - (double)pContext.getClickedPos().getZ());
        return new Vec3(x, y, z);
    }

    /**
     * クリックしたブロックの位置からプレイヤーの位置を引いた座標を取得
     */
    public static Vec3 getPlayerPosition(BlockPlaceContext pContext) {
        if(pContext.getPlayer() == null) {
            return null;
        }
        double x = getFloorDot2(pContext.getClickLocation().x - pContext.getPlayer().getX());
        double y = getFloorDot2(pContext.getClickLocation().y - pContext.getPlayer().getY());
        double z = getFloorDot2(pContext.getClickLocation().z - pContext.getPlayer().getZ());
        return new Vec3(x, y, z);
    }

    /**
     * 小数第3位で切り捨て
     */
    private static double getFloorDot2(double d) {
        return Math.floor(d * 100) / 100;
    }

    public record Point(double x, double y, double z) {}
}
