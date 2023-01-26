# Complete Compost
###### A mod for Minecraft Forge about compost. That's it. Plants go brrrrr.

## What?
Adds three blocks that speed up plant growth.

 - ### Compost
  Speeds up the growth of crops above it by redirecting any random ticks it receives. It can stack infinitely as long as there is only compost or farmland between it and the crop. Can be waterlogged which improves growth rates (for plants that want water, like wheat) and allows sugar cane to be planted on it without a nearby water source.
  
 - ### Luminant Compost
  Speeds up the growth of crops above it by redirecting any random ticks it receives. This variant also glows like glowstone! It can stack infinitely as long as there is only compost or farmland between it and the crop. Can be waterlogged which improves growth rates (for plants that want water, like wheat) and allows sugar cane to be planted on it without a nearby water source. 

 - ### Mystical Compost
  Speeds up the growth of any block above it by redirecting random ticks it receives. This means it can speed up blocks like budding amethyst. Just like regular Compost it can stack infinitely as long as there is only compost or farmland between it and the crop. Can be waterlogged which improves growth rates and allows sugar cane to be planted on it without a nearby water source.

## How?
You can obtain compost by right-clicking any dirt block on a composter, it will consume one level of compost. Craft a piece of compost with 4 amethyst shards to get two mystical compost.



### Tags:
##### Complete Compost is very configurable with tags.
`#complete_compost:crop_allowlist` is a block tag for crops that do not extend work by default, but you still want to count as crops.\
`#complete_compost:crop_denylist` is for crops that are considered crops by default, but do now want to count as crops. It overrides the whitelist if a block is in both.\
`#complete_compost:mystical_denylist` is for blocks that you do not want to allow mystical compost to tick\
`#complete_compost:farmland` is for blocks that you want to allow the tick to pass through when going up.\
`#minecraft:dirt` is the item tag used to determine what items can be used to make compost.\
`#complete_compost:dirt_denylist` is for items that are tagged with `minecraft:dirt` that you do not want to be able to create compost, like moss. It is recommended any compostables be in this tag, otherwise issues may happen.\
`#complete_compost:tall_crops` is a block tag for crops like sugar cane and bamboo that grow multiple blocks high and so need special handling to tick the top block, rather than the bottom one.
