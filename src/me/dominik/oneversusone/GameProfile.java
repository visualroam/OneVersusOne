package me.dominik.oneversusone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dominik on 17.09.2016.
 */
@AllArgsConstructor public class GameProfile{

    @Getter String UUID;
    @Getter String NAME;
    @Getter @Setter Integer Elo;
    @Getter @Setter Integer Wins;
    @Getter @Setter Integer Lose;



}
