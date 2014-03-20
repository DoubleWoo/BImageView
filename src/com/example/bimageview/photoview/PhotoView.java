/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.bimageview.photoview;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.bimageview.photoview.PhotoViewAttacher.OnMatrixChangedListener;
import com.example.bimageview.photoview.PhotoViewAttacher.OnPhotoTapListener;
import com.example.bimageview.photoview.PhotoViewAttacher.OnViewTapListener;
import com.example.bimageview.smart.ContactImage;
import com.example.bimageview.smart.SmartImage;
import com.example.bimageview.smart.SmartImageTask;
import com.example.bimageview.smart.WebImage;

public class PhotoView extends ImageView implements IPhotoView {

	private final PhotoViewAttacher mAttacher;

	private ScaleType mPendingScaleType;

	public PhotoView(Context context) {
		this(context, null);
	}

	public PhotoView(Context context, AttributeSet attr) {
		this(context, attr, 0);
	}
	
	public PhotoView(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
		super.setScaleType(ScaleType.MATRIX);
		mAttacher = new PhotoViewAttacher(this);

		if (null != mPendingScaleType) {
			setScaleType(mPendingScaleType);
			mPendingScaleType = null;
		}
	}

	@Override
	public boolean canZoom() {
		return mAttacher.canZoom();
	}

	@Override
	public RectF getDisplayRect() {
		return mAttacher.getDisplayRect();
	}

	@Override
	public float getMinScale() {
		return mAttacher.getMinScale();
	}

	@Override
	public float getMidScale() {
		return mAttacher.getMidScale();
	}

	@Override
	public float getMaxScale() {
		return mAttacher.getMaxScale();
	}

	@Override
	public float getScale() {
		return mAttacher.getScale();
	}

	@Override
	public ScaleType getScaleType() {
		return mAttacher.getScaleType();
	}

    @Override
    public void setAllowParentInterceptOnEdge(boolean allow) {
        mAttacher.setAllowParentInterceptOnEdge(allow);
    }

    @Override
	public void setMinScale(float minScale) {
		mAttacher.setMinScale(minScale);
	}

	@Override
	public void setMidScale(float midScale) {
		mAttacher.setMidScale(midScale);
	}

	@Override
	public void setMaxScale(float maxScale) {
		mAttacher.setMaxScale(maxScale);
	}

	@Override
	// setImageBitmap calls through to this method
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		if (null != mAttacher) {
			mAttacher.update();
		}
	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		if (null != mAttacher) {
			mAttacher.update();
		}
	}

	@Override
	public void setImageURI(Uri uri) {
		super.setImageURI(uri);
		if (null != mAttacher) {
			mAttacher.update();
		}
	}
	//====================================================================
	//加入直接加载URL的方法
		  private static final int LOADING_THREADS = 4;
		    private static ExecutorService threadPool = Executors.newFixedThreadPool(LOADING_THREADS);

		    private SmartImageTask currentTask;
	    // Helpers to set image by URL
	    public void setImageUrl(String url) {
	        setImage(new WebImage(url));
	    }

	    public void setImageUrl(String url, SmartImageTask.OnCompleteListener completeListener) {
	        setImage(new WebImage(url), completeListener);
	    }

	    public void setImageUrl(String url, final Integer fallbackResource) {
	        setImage(new WebImage(url), fallbackResource);
	    }

	    public void setImageUrl(String url, final Integer fallbackResource, SmartImageTask.OnCompleteListener completeListener) {
	        setImage(new WebImage(url), fallbackResource, completeListener);
	    }

	    public void setImageUrl(String url, final Integer fallbackResource, final Integer loadingResource) {
	        setImage(new WebImage(url), fallbackResource, loadingResource);
	    }

	    public void setImageUrl(String url, final Integer fallbackResource, final Integer loadingResource, SmartImageTask.OnCompleteListener completeListener) {
	        setImage(new WebImage(url), fallbackResource, loadingResource, completeListener);
	    }


	    // Helpers to set image by contact address book id
	    public void setImageContact(long contactId) {
	        setImage(new ContactImage(contactId));
	    }

	    public void setImageContact(long contactId, final Integer fallbackResource) {
	        setImage(new ContactImage(contactId), fallbackResource);
	    }

	    public void setImageContact(long contactId, final Integer fallbackResource, final Integer loadingResource) {
	        setImage(new ContactImage(contactId), fallbackResource, fallbackResource);
	    }


	    // Set image using SmartImage object
	    public void setImage(final SmartImage image) {
	        setImage(image, null, null, null);
	    }

	    public void setImage(final SmartImage image, final SmartImageTask.OnCompleteListener completeListener) {
	        setImage(image, null, null, completeListener);
	    }

	    public void setImage(final SmartImage image, final Integer fallbackResource) {
	        setImage(image, fallbackResource, fallbackResource, null);
	    }

	    public void setImage(final SmartImage image, final Integer fallbackResource, SmartImageTask.OnCompleteListener completeListener) {
	        setImage(image, fallbackResource, fallbackResource, completeListener);
	    }

	    public void setImage(final SmartImage image, final Integer fallbackResource, final Integer loadingResource) {
	        setImage(image, fallbackResource, loadingResource, null);
	    }

	    public void setImage(final SmartImage image, final Integer fallbackResource, final Integer loadingResource, final SmartImageTask.OnCompleteListener completeListener) {
	        // Set a loading resource
	        if(loadingResource != null){
	            setImageResource(loadingResource);
	        }

	        // Cancel any existing tasks for this image view
	        if(currentTask != null) {
	            currentTask.cancel();
	            currentTask = null;
	        }

	        // Set up the new task
	        currentTask = new SmartImageTask(getContext(), image);
	        currentTask.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {
	            @Override
	            public void onComplete(Bitmap bitmap) {
	                if(bitmap != null) {
	                    setImageBitmap(bitmap);
	                } else {
	                    // Set fallback resource
	                    if(fallbackResource != null) {
	                        setImageResource(fallbackResource);
	                    }
	                }

	                if(completeListener != null){
	                    completeListener.onComplete();
	                }
	            }
	        });

	        // Run the task in a threadpool
	        threadPool.execute(currentTask);
	    }

	    public static void cancelAllTasks() {
	        threadPool.shutdownNow();
	        threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
	    }
	    
	    
	    //===============================================================
	@Override
	public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
		mAttacher.setOnMatrixChangeListener(listener);
	}

	@Override
	public void setOnLongClickListener(OnLongClickListener l) {
		mAttacher.setOnLongClickListener(l);
	}

	@Override
	public void setOnPhotoTapListener(OnPhotoTapListener listener) {
		mAttacher.setOnPhotoTapListener(listener);
	}

	@Override
	public void setOnViewTapListener(OnViewTapListener listener) {
		mAttacher.setOnViewTapListener(listener);
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		if (null != mAttacher) {
			mAttacher.setScaleType(scaleType);
		} else {
			mPendingScaleType = scaleType;
		}
	}

	@Override
	public void setZoomable(boolean zoomable) {
		mAttacher.setZoomable(zoomable);
	}

	@Override
	public void zoomTo(float scale, float focalX, float focalY) {
		mAttacher.zoomTo(scale, focalX, focalY);
	}

	@Override
	protected void onDetachedFromWindow() {
		mAttacher.cleanup();
		super.onDetachedFromWindow();
	}

}