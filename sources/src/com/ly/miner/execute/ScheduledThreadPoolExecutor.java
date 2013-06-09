/**
 * 
 */
package com.ly.miner.execute;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zhanjie
 *
 */
public final class ScheduledThreadPoolExecutor extends IExecutor implements ScheduledExecutorService  {
	
	public ScheduledThreadPoolExecutor(String threadName) {
		this.executor = java.util.concurrent.Executors.newScheduledThreadPool(1,new MCAThreadFactory(threadName));
		this.threadName = threadName;
	}

	@Override
	public void shutdown() {
		this.executor.shutdown();
		
	}

	@Override
	public List<Runnable> shutdownNow() {
		return this.executor.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		
		return this.executor.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		
		return this.executor.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit)
			throws InterruptedException {
		
		return this.executor.awaitTermination(timeout, unit);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return this.executor.submit(task);
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		return this.executor.submit(task, result);
	}

	@Override
	public Future<?> submit(Runnable task) {
		return this.executor.submit(task);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
			throws InterruptedException {
		return this.executor.invokeAll(tasks);
	}

	@Override
	public <T> List<Future<T>> invokeAll(
			Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		return this.executor.invokeAll(tasks, timeout, unit);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
			throws InterruptedException, ExecutionException {
		return this.executor.invokeAny(tasks);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks,
			long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {
		return this.executor.invokeAny(tasks, timeout, unit);
	}

	@Override
	public ScheduledFuture<?> schedule(Runnable command, long delay,
			TimeUnit unit) {
		
		return ((ScheduledExecutorService)this.executor).schedule(command, delay, unit);
	}

	@Override
	public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay,
			TimeUnit unit) {
		return ((ScheduledExecutorService)this.executor).schedule(callable, delay, unit);
	}

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
			long initialDelay, long period, TimeUnit unit) {
		
		return ((ScheduledExecutorService)this.executor).scheduleAtFixedRate(command, initialDelay, period, unit);
	}

	@Override
	public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
			long initialDelay, long delay, TimeUnit unit) {
		return ((ScheduledExecutorService)this.executor).scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}

}
