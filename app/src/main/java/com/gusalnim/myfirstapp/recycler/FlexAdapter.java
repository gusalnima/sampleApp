package com.gusalnim.myfirstapp.recycler;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.gusalnim.myfirstapp.recycler.filter.BaseFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by gusalnim on 30/04/2019.
 */
public class FlexAdapter extends RecyclerView.Adapter<ItemRenderer> implements Filterable {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Item {
    }

    private final RendererFactory RENDERER_FACTORY;

    private RecyclerView recyclerView;

    public FlexAdapter(RendererFactory rendererFactory) {
        RENDERER_FACTORY = rendererFactory;
        ITEM_STORAGE = new ItemStore();
    }

    //----------------------------------------
    // view holder
    //----------------------------------------

    @Override
    public ItemRenderer onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("gusalnim","123123");
        recyclerView = (RecyclerView) parent;

        if (itemTouchHelperCallback == null) {
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    if (null != itemTouchHelperCallback) return;
                    itemTouchHelperCallback = new ItemTouchHelperCallback();
                    itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                }
            });
        }

        return RENDERER_FACTORY.createItemRenderer(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final ItemRenderer holder, int position) {
        holder.flexAdapter = this;
        holder.onClickListener = onClickListener;
        holder.onLongClickListener = onLongClickListener;

        if (position == 0) holder.setPrevItem(null);
        else holder.setPrevItem(getItem(position - 1));

        if (position + 1 < getItemCount()) holder.setNextItem(getItem(position + 1));
        else holder.setNextItem(null);

        holder.itemView.setOnClickListener(null);
        holder.itemView.setOnLongClickListener(null);

        holder.setCurrentItem(getItem(position));

        if (holder.itemView.hasOnClickListeners() == false) {
            holder.itemView.setOnClickListener(onClickListener);
            holder.itemView.setOnLongClickListener(onLongClickListener);
        }
    }

    @Override
    public void onViewAttachedToWindow(ItemRenderer holder) {
        super.onViewAttachedToWindow(holder);
        holder.onAttachedRenderer();
    }

    @Override
    public void onViewDetachedFromWindow(ItemRenderer holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onDetachedRenderer();
    }

    @Override
    public boolean onFailedToRecycleView(ItemRenderer holder) {
        holder.onIdle();
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewRecycled(ItemRenderer holder) {
        super.onViewRecycled(holder);
        holder.onIdle();
    }

    @Override
    public int getItemViewType(int position) {
        final Object item = getItem(position);

        for (Method method : item.getClass().getMethods()) {
            if (method.isAnnotationPresent(TypeStore.DefineItem.class)) {
                try {
                    return (int) method.invoke(item);
                } catch (Exception ignored) {
                }
            }
        }

        return TypeStore.getInstance().getType(item.getClass());
    }

    public int getViewPosition(View view) {
        view = getItemView(view);
        if (null != view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            return layoutParams.getViewAdapterPosition();
        }

        return -1;
    }

    private View getItemView(View childView) {
        while (childView != null) {
            if (childView.getLayoutParams() instanceof RecyclerView.LayoutParams) {
                return childView;
            }

            childView = (View) childView.getParent();
        }

        return null;
    }

    int getRecyclerViewWidth() {
        if (null == recyclerView) return -1;
        return recyclerView.getWidth();
    }

    int getRecyclerViewHeight() {
        if (null == recyclerView) return -1;
        return recyclerView.getHeight();
    }

    //----------------------------------------
    // model
    //----------------------------------------

    private final ItemStore ITEM_STORAGE;

    public void setModels(List<?> list) {
        ITEM_STORAGE.clear();
        ITEM_STORAGE.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        ITEM_STORAGE.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ITEM_STORAGE.size();
    }

    public Object getItem(int position) {
        return ITEM_STORAGE.get(position);
    }

    public int getItemPosition(Object listItem) {
        return ITEM_STORAGE.indexOfVisible(listItem);
    }

    public void clearAllItem() {
        if (null != ITEM_STORAGE) {
            ITEM_STORAGE.clear();
            notifyDataSetChanged();
        }
    }

    public void addItem(int position, Object item) {
        ITEM_STORAGE.add(position, item);
        notifyItemInserted(position);
    }

    public void addItem(Object item) {
        ITEM_STORAGE.add(item);
        notifyItemInserted(ITEM_STORAGE.size());
    }

    public Object removeItem(Object item) {
        int position = getItemPosition(item);
        return removeItem(position);
    }

    public Object removeItem(int position) {
        Object item = null;

        if (position != -1) {
            item = ITEM_STORAGE.remove(position);
            notifyItemRemoved(position);
        }

        return item;
    }

    public void removeItems(int start, int end) {
        final int removeCount = end - start + 1;
        if (removeCount <= 0) return;

        while (start <= end) {
            ITEM_STORAGE.remove(start);
            end--;
        }

        notifyItemRangeRemoved(start, removeCount);
    }

    public void replaceItem(Object oldItem, Object newItem) {
        int position = getItemPosition(oldItem);
        replaceItem(position, newItem);
    }

    public void replaceItem(int position, Object newItem) {
        if (position != -1) {
            ITEM_STORAGE.remove(position);
            ITEM_STORAGE.add(position, newItem);
            notifyItemChanged(newItem);
        }
    }

    public boolean swapItem(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(ITEM_STORAGE, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(ITEM_STORAGE, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    //----------------------------------------
    // notify
    //----------------------------------------

    public void notifyItemChanged(Object object) {
        int position = getItemPosition(object);
        if (position == -1) return;

        if (null != recyclerView) {
            View view;
            for (int index = 0; index < recyclerView.getChildCount(); index++) {
                view = recyclerView.getChildAt(index);
                if (position == getViewPosition(view)) {
                    ItemRenderer itemRenderer = (ItemRenderer) recyclerView.getChildViewHolder(view);
                    onBindViewHolder(itemRenderer, position);
                    return;
                }
            }
        }

        notifyItemChanged(position);
    }

    public void notifyItemInserted(Object object) {
        int position = getItemPosition(object);
        if (position == -1) return;
        notifyItemInserted(position);
    }

    public void notifyItemRemoved(Object object) {
        int position = getItemPosition(object);
        if (position == -1) return;
        notifyItemRemoved(position);
    }

    //----------------------------------------
    // select
    //----------------------------------------

    private boolean isSelectMode;

    public boolean isSelectMode() {
        return isSelectMode;
    }

    public void setSelectMode(boolean mode) {
        setSelectMode(mode, false);
    }

    public void setSelectMode(boolean mode, boolean clearSelected) {
        int removeSize = 0;
        if (clearSelected) removeSize = ITEM_STORAGE.clearSelectedList();

        boolean oldMode = isSelectMode;
        isSelectMode = mode;

        if (removeSize > 0 || isSelectMode != oldMode) notifyDataSetChanged();
    }

    public void addSelectItem(Object object) {
        if (ITEM_STORAGE.contains(object))
            ITEM_STORAGE.addSelectItem(object);
    }

    public void removeSelectItem(Object object) {
        if (ITEM_STORAGE.contains(object))
            ITEM_STORAGE.removeSelectItem(object);
    }

    public boolean isSelected(Object object) {
        return ITEM_STORAGE.isSelected(object);
    }

    public boolean toggleSelectItem(Object object) {
        boolean result = false;

        if (ITEM_STORAGE.contains(object)) {
            result = ITEM_STORAGE.toggleSelectItem(object);
            notifyItemChanged(object);
        }

        return result;
    }

    public List<Object> getSelectedItemList() {
        return ITEM_STORAGE.getSelectedItemList();
    }

    //----------------------------------------
    // filter
    //----------------------------------------

    private Class filterClass = BaseFilter.class;

    public <F extends BaseFilter> void setFilter(Class<F> filterClass) {
        this.filterClass = filterClass;
    }

    @Override
    public Filter getFilter() {
        if (null == filterClass) {
            throw new RuntimeException("Set filter class (e.g., adapter.setFilter(class))");
        }

        try {
            Constructor constructor = filterClass.getConstructor(getClass(), ItemStore.class);
            return (Filter) constructor.newInstance(this, ITEM_STORAGE);

        } catch (NoSuchMethodException e) {
            throw new RuntimeException("constructor cannot be found", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("abstract class or interface", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("constructor method no access", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("checkout package-name", e);
        }
    }

    public List<Object> getHideItemList() {
        return ITEM_STORAGE.getHidedItemList();
    }

    //----------------------------------------
    // click event
    //----------------------------------------

    private OnItemClickEventHandler onItemClickEventHandler;

    public void setOnItemClickEventHandler(OnItemClickEventHandler handler) {
        onItemClickEventHandler = handler;
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (null == onItemClickEventHandler) return;

            int position = getViewPosition(view);
            if (position == -1) return;
            final Object item = getItem(position);

            if (isItemView(view)) onItemClickEventHandler.onItemClick(item, view);
            else onItemClickEventHandler.onChildViewClick(item, getItemView(view), view.getId());
        }
    };

    private final View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (null == onItemClickEventHandler) return true;

            int position = getViewPosition(view);
            if (position == -1) return false;
            final Object item = getItem(position);

            if (isItemView(view)) onItemClickEventHandler.onItemLongClick(item, view);
            else onItemClickEventHandler.onChildViewLongClick(item, getItemView(view), view.getId());

            return true;
        }
    };

    private boolean isItemView(View view) {
        return view.getLayoutParams() instanceof RecyclerView.LayoutParams;
    }

    //----------------------------------------
    // itemDrag
    //----------------------------------------

    private boolean itemDrag = false;

    public boolean isItemDrag() {
        return itemDrag;
    }

    public void setItemDrag(boolean itemDrag) {
        this.itemDrag = itemDrag;
    }

    private OnItemDragAndDropHandler onItemDragAndDropHandler;

    public OnItemDragAndDropHandler getOnItemDragAndDrop() {
        return onItemDragAndDropHandler;
    }

    public void setOnItemDragAndDropHandler(OnItemDragAndDropHandler handler) {
        onItemDragAndDropHandler = handler;
    }

    public interface OnItemDragAndDropHandler {
        void onChanged(int fromPosition, int toPosition);
    }

    //----------------------------------------
    // itemSwipe
    //----------------------------------------

    private boolean itemSwipe = false;

    public boolean isItemSwipe() {
        return itemSwipe;
    }

    public void setItemSwipe(boolean itemSwipe) {
        this.itemSwipe = itemSwipe;
    }

    private OnItemSwipeHandler onItemSwipeHandler;

    public OnItemSwipeHandler getOnItemSwipeHandler() {
        return onItemSwipeHandler;
    }

    public void setOnItemSwipe(OnItemSwipeHandler handler) {
        onItemSwipeHandler = handler;
    }

    public interface OnItemSwipeHandler {
        @IntDef({REMOVE_ITEM, RESTORE_ITEM, HOLD_ITEM})
        @interface AfterAction {
        }

        int REMOVE_ITEM = 1;
        int RESTORE_ITEM = 2;
        int HOLD_ITEM = 3;

        @AfterAction
        int onSwipe(Object item);
    }

    //----------------------------------------
    // ItemTouchHelper
    //----------------------------------------

    ItemTouchHelper itemTouchHelper;
    private ItemTouchHelperCallback itemTouchHelperCallback;

    public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

        private ItemTouchHelperCallback() {
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return itemDrag;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return itemSwipe;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            // TODO: 2018. 3. 13. 특정 viewholder만 이동 가능 하도록
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            final int from = viewHolder.getAdapterPosition();
            final int to = target.getAdapterPosition();

            swapItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            if (null != onItemDragAndDropHandler) onItemDragAndDropHandler.onChanged(from, to);

            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            final Object item = getItem(position);

            if (null == item) return;

            if (null != onItemSwipeHandler) {
                final int result = onItemSwipeHandler.onSwipe(item);
                switch (result) {
                    case OnItemSwipeHandler.REMOVE_ITEM:
                        removeItem(item);
                        break;

                    case OnItemSwipeHandler.RESTORE_ITEM:
                        notifyItemChanged(position);
                        break;

                    case OnItemSwipeHandler.HOLD_ITEM:
                        break;

                }
            } else {
                removeItem(item);
            }
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            final int position = viewHolder.getAdapterPosition();
            if (position == -1) return;

            if (null != onChildDrawer) {
                RectF rect;

                int leftMargin = 0;
                int rightMargin = 0;
                if (viewHolder.itemView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) viewHolder.itemView.getLayoutParams();
                    leftMargin = layoutParams.leftMargin;
                    rightMargin = layoutParams.rightMargin;
                }

                if (dX > 0) rect = new RectF(viewHolder.itemView.getLeft() - leftMargin, viewHolder.itemView.getTop(), dX + leftMargin, viewHolder.itemView.getBottom());
                else rect = new RectF(viewHolder.itemView.getRight() + dX, viewHolder.itemView.getTop(), viewHolder.itemView.getRight() + rightMargin, viewHolder.itemView.getBottom());
                rect.left = Math.max(0, rect.left);
                rect.right = Math.min(c.getWidth(), rect.right);

                onChildDrawer.onDraw(c, recyclerView, viewHolder, rect, getItem(position), dX, dY, actionState, isCurrentlyActive);
            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    //----------------------------------------
    // OnChildDrawer
    //----------------------------------------

    private OnChildDrawer onChildDrawer;

    public OnChildDrawer getOnChildDraw() {
        return onChildDrawer;
    }

    public void setOnChildDraw(OnChildDrawer onChildDrawer) {
        this.onChildDrawer = onChildDrawer;
    }

    public interface OnChildDrawer {
        void onDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RectF rect, Object item, float dX, float dY, int actionState, boolean isCurrentlyActive);

//		void onDetached(RecyclerView.ViewHolder viewHolder, Object item); //이걸 어찌 하리오
    }

}