package com.glistre.glistremod.projectiles.tobyworstsword;

import com.google.common.collect.Multimap;

public interface IExtendedReach {

	//create interface to make toby sword have better reach for better damage on mob boss
        public float getReach(); // default is 1.0D

		Multimap getItemAttributeModifiers();
   
}
