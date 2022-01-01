/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package diablo.unit

import diablo.Position
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach

internal class HeroTest {

    lateinit var monster: Monster
    lateinit var hero: Hero

    @BeforeEach
    fun setup(){
        monster = Monster.Builer()
            .setAttackRange(10.0)
            .setPos(Position(0.0,0.0))
            .setState(MonsterState(10,10))
            .build()

        hero = Hero(HeroState(100,10),Position(0.0,0.0),10.0)
    }

    @Test
    fun attacked() {
        hero.attacked(monster)
        val life = hero.life
        Assertions.assertEquals(90,life)
    }
}