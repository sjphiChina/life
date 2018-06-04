package sjph.life.user.utils.hystrix;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import sjph.life.user.utils.UserContext;
import sjph.life.user.utils.UserContextHolder;


public final class DelegatingUserContextCallable<V> implements Callable<V> {
    private static final Logger logger = LoggerFactory.getLogger(DelegatingUserContextCallable.class);
    private final Callable<V> delegate;



    //private final UserContext delegateUserContext;
    private UserContext originalUserContext;

    public DelegatingUserContextCallable(Callable<V> delegate,
                                             UserContext userContext) {
        Assert.notNull(delegate, "delegate cannot be null");
        Assert.notNull(userContext, "userContext cannot be null");
        this.delegate = delegate;
        this.originalUserContext = userContext;
        logger.debug("DelegatingUserContextCallable _ CorrelationId: {}, threadId: {}", originalUserContext.getCorrelationId(), Thread.currentThread().getId());
    }

    public DelegatingUserContextCallable(Callable<V> delegate) {
        this(delegate, UserContextHolder.getContext());
    }

    public V call() throws Exception {
        logger.debug("call _ CorrelationId: {}, threadId: {}", originalUserContext.getCorrelationId(), Thread.currentThread().getId());
        
        UserContextHolder.setContext( originalUserContext );

        try {
            return delegate.call();
        }
        finally {

            this.originalUserContext = null;
        }
    }

    public String toString() {
        return delegate.toString();
    }


    public static <V> Callable<V> create(Callable<V> delegate,
                                         UserContext userContext) {
        logger.debug("create _ CorrelationId: {}, threadId: {}", userContext.getCorrelationId(), Thread.currentThread().getId());
        return new DelegatingUserContextCallable<V>(delegate, userContext);
    }
}
