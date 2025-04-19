package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Title: DiceData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 21:58
 * @description: 戳一戳消息段 data 类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.POKE)
public class PokeData extends MessageData {
    // 见 [Mirai 的 PokeMessage 类](https://github.com/mamoe/mirai/blob/f5eefae7ecee84d18a66afce3f89b89fe1584b78/mirai-core/src/commonMain/kotlin/net.mamoe.mirai/message/data/HummerMessage.kt#L49)
    private String type;
    // 同上
    private String id;
    // 同上
    private String name;

    @Override
    public String toString() {
        return "[戳一戳消息<type=" + this.type + "><name=" + this.id + "><name=" + this.name + ">]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokeData pokeData = (PokeData) o;
        return Objects.equals(type, pokeData.type) && Objects.equals(id, pokeData.id) && Objects.equals(name, pokeData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id, name);
    }
}
