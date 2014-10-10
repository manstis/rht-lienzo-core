/*
   Copyright (c) 2012 Emitrom LLC. All rights reserved. 
   For licensing questions, please contact us at licensing@emitrom.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.emitrom.lienzo.client.core.animation;

/**
 * The AnimationTweener defines how the attributes change over time.
 * <p>
 * E.g. AnimationTweener.LINEAR applies a linear change from the original value to the resulting value. 
 * E.g. if the initial value is 100 and the target value is 200, the intermediate value would be 
 * <code>100 + p * (200 - 100)</code>, where p is a value that grows from 0 to 1 over the duration of the animation.
 * <p>
 * The AnimationTweener class provides several predefined tweeners, but you can customize them with TweenerBuilder. 
 * Or you can create you own AnimationTweener from scratch.
 * 
 * @see AnimationManager#add(com.emitrom.lienzo.client.core.shape.IPrimitive, AnimationTweener, AnimationProperties, int, IAnimationCallback)
 * @see com.emitrom.lienzo.client.core.shape.IPrimitive#animate(AnimationTweener, AnimationProperties, int)
 * @see com.emitrom.lienzo.client.core.shape.IPrimitive#animate(AnimationTweener, AnimationProperties, int, IAnimationCallback)
 */
public interface AnimationTweener
{
    public static final AnimationTweener LINEAR      = TweenerBuilder.MAKE_LINEAR();

    public static final AnimationTweener EASE_IN     = TweenerBuilder.MAKE_EASE_IN(3.0);

    public static final AnimationTweener EASE_OUT    = TweenerBuilder.MAKE_EASE_OUT(3.0);

    public static final AnimationTweener EASE_IN_OUT = TweenerBuilder.MAKE_EASE_IN_OUT();

    public static final AnimationTweener ELASTIC     = TweenerBuilder.MAKE_ELASTIC(3);

    public static final AnimationTweener BOUNCE      = TweenerBuilder.MAKE_BOUNCE(3);

    public double tween(double percent);

    public static final class TweenerBuilder
    {
        public static final AnimationTweener MAKE_EASE_IN(double strength)
        {
            return MAKE_EASE_IN_P(Math.min(6.0, Math.max(1.0, strength)));
        }

        public static final AnimationTweener MAKE_EASE_OUT(double strength)
        {
            return MAKE_EASE_OUT_P(Math.min(6.0, Math.max(1.0, strength)));
        }

        private static final AnimationTweener MAKE_LINEAR()
        {
            return new AnimationTweener()
            {
                @Override
                public double tween(double percent)
                {
                    return percent;
                }
            };
        }

        private static final AnimationTweener MAKE_EASE_IN_P(final double strength)
        {
            return new AnimationTweener()
            {
                @Override
                public double tween(double percent)
                {
                    return Math.pow(percent, strength * 2.0);
                }
            };
        }

        private static final AnimationTweener MAKE_EASE_OUT_P(final double strength)
        {
            return new AnimationTweener()
            {
                @Override
                public double tween(double percent)
                {
                    return (1.0 - Math.pow(1.0 - percent, strength * 2.0));
                }
            };
        }

        private static final AnimationTweener MAKE_EASE_IN_OUT()
        {
            return new AnimationTweener()
            {
                @Override
                public double tween(double percent)
                {
                    return (percent - Math.sin(percent * 2.0 * Math.PI) / (2.0 * Math.PI));
                }
            };
        }

        public static final AnimationTweener MAKE_ELASTIC(final int passes)
        {
            return new AnimationTweener()
            {
                @Override
                public double tween(double percent)
                {
                    return (((1.0 - Math.cos(percent * Math.PI * passes)) * (1.0 - percent)) + percent);
                }
            };
        }

        public static final AnimationTweener MAKE_BOUNCE(int bounces)
        {
            final AnimationTweener elastic = MAKE_ELASTIC(bounces);

            return new AnimationTweener()
            {
                @Override
                public double tween(double percent)
                {
                    percent = elastic.tween(percent);

                    return ((percent <= 1.0) ? (percent) : (2.0 - percent));
                }
            };
        }
    }
}
