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

package com.emitrom.lienzo.client.core.shape;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.i18n.MessageConstants;
import com.emitrom.lienzo.client.core.shape.json.IFactory;
import com.emitrom.lienzo.client.core.shape.json.ShapeFactory;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.emitrom.lienzo.shared.core.types.ShapeType;
import com.emitrom.lienzo.shared.core.types.TextAlign;
import com.emitrom.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.event.dom.client.CanPlayThroughEvent;
import com.google.gwt.event.dom.client.CanPlayThroughHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.media.client.Video;
import com.google.gwt.media.dom.client.MediaError;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Movie provides a mechanism for viewing and controlling videos in a Canvas.
 * Due to discrepancies in the adoption of the Canvas specification by different vendors,
 * you should provide multiple formats of the video to ensure portability.
 */
public class Movie extends Shape<Movie>
{
    private boolean m_added = false;

    private boolean m_inits = false;

    private boolean m_pause = false;

    private String  m_error = null;

    private Video   m_video = Video.createIfSupported();

    /**
     * Constructor. Creates an instance of a movie.
     * 
     * @param url
     */
    public Movie(String url)
    {
        super(ShapeType.MOVIE);

        getAttributes().setURL(url);

        if (null != m_video)
        {
            m_video.setSrc(getURL());

            m_video.setLoop(false);

            m_video.setVisible(false);

            m_video.setPreload(MediaElement.PRELOAD_AUTO);
        }
    }

    protected Movie(JSONObject node)
    {
        super(ShapeType.MOVIE, node);
    }

    /**
     * Draws the frames of the video.  If looping has been set, frames are drawn
     * continuously in a loop.
     * 
     * @param context
     */
    @Override
    public boolean prepare(final Context2D context, Attributes attr, double alpha)
    {
        if (false == m_inits)
        {
            init();
        }
        if (null != m_error)
        {
            context.save();

            context.setTextAlign(TextAlign.LEFT);

            context.setTextBaseline(TextBaseLine.ALPHABETIC);

            context.setTextFont("italic 40pt Calibri");

            context.setStrokeColor(ColorName.BLACK);

            context.setStrokeWidth(2);

            context.strokeText(m_error, 0, 0);

            context.restore();
        }
        else
        {
            final int wide = getWidth();

            final int high = getHeight();

            m_video.setWidth(wide + "px");

            m_video.setHeight(high + "px");

            m_video.setLoop(isLoop());

            if (false == m_added)
            {
                m_added = true;

                RootPanel.get().add(m_video);
            }
            m_video.addCanPlayThroughHandler(new CanPlayThroughHandler()
            {
                @Override
                public void onCanPlayThrough(CanPlayThroughEvent event)
                {
                    play();

                    AnimationScheduler.get().requestAnimationFrame(new AnimationCallback()
                    {
                        @Override
                        public void execute(double timestamp)
                        {
                            if (false == m_pause)
                            {
                                context.drawImage(m_video.getElement(), getX(), getY(), getWidth(), getHeight());

                                AnimationScheduler.get().requestAnimationFrame(this);
                            }
                        }
                    });
                }
            });
        }
        return false;
    }

    /**
     * Sets the movie's volume
     * 
     * @param volume
     * @return this Movie
     */
    public Movie setVolume(double volume)
    {
        getAttributes().setVolume(volume);

        if (null != m_video)
        {
            m_video.setVolume(getVolume());
        }
        return this;
    }

    /**
     * Gets the value for the volume.
     * 
     * @return double
     */
    public double getVolume()
    {
        return getAttributes().getVolume();
    }

    /**
     * Plays the video.
     * 
     * @return this Movie
     */
    public Movie play()
    {
        m_pause = false;

        if (null != m_video)
        {
            m_video.play();
        }
        return this;
    }

    /**
     * Gets the URL for this movie.
     * 
     * @return String
     */
    public String getURL()
    {
        return getAttributes().getURL();
    }

    /**
     * Pauses this movie.
     * 
     * @return this Movie
     */
    public Movie pause()
    {
        m_pause = true;

        if (null != m_video)
        {
            m_video.pause();
        }
        return this;
    }

    /**
     * Sets the movie to continuously loop or not.
     * 
     * @param loop
     * @return this Movie
     */
    public Movie setLoop(boolean loop)
    {
        getAttributes().setLoop(loop);

        return this;
    }

    /**
     * Returns true if this movie is set to loop; false otherwise.
     * 
     * @return boolean
     */
    public boolean isLoop()
    {
        return getAttributes().isLoop();
    }

    /**
     * Sets the width of this movie's display area
     * 
     * @param wide
     * @return this Movie
     */
    public Movie setWidth(int wide)
    {
        getAttributes().setWidth(wide);

        return this;
    }

    /**
     * Gets the width of this movie's display area
     * 
     * @return int
     */
    public int getWidth()
    {
        return (int) (getAttributes().getWidth() + 0.5);
    }

    /**
     * Sets the height of this movie's display area
     * 
     * @param high
     * @return this Movie
     */
    public Movie setHeight(int high)
    {
        getAttributes().setHeight(high);

        return this;
    }

    /**
     * Gets the height of this movie's display area
     * 
     * @return int
     */
    public int getHeight()
    {
        return (int) (getAttributes().getHeight() + 0.5);
    }

    private final void init()
    {
        if (null != m_video)
        {
            MediaError status = m_video.getError();

            if (status != null)
            {
                switch (status.getCode())
                {
                    case MediaError.MEDIA_ERR_ABORTED:
                        m_error = MessageConstants.MESSAGES.moviePlaybackWasAborted();
                    break;
                    case MediaError.MEDIA_ERR_NETWORK:
                        m_error = MessageConstants.MESSAGES.movieNetworkError();
                    break;
                    case MediaError.MEDIA_ERR_DECODE:
                        m_error = MessageConstants.MESSAGES.movieErrorInDecoding();
                    break;
                    case MediaError.MEDIA_ERR_SRC_NOT_SUPPORTED:
                        m_error = MessageConstants.MESSAGES.movieFormatNotSupported();
                    break;
                }
            }
        }
        else
        {
            m_error = MessageConstants.MESSAGES.movieNotSupportedInThisBrowser();
        }
        m_inits = true;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new MovieFactory();
    }

    public static class MovieFactory extends ShapeFactory<Movie>
    {
        public MovieFactory()
        {
            super(ShapeType.MOVIE);

            addAttribute(Attribute.URL, true);

            addAttribute(Attribute.LOOP);

            addAttribute(Attribute.WIDTH);

            addAttribute(Attribute.HEIGHT);

            addAttribute(Attribute.VOLUME);
        }

        @Override
        public Movie create(JSONObject node, ValidationContext ctx)
        {
            return new Movie(node);
        }
    }
}
