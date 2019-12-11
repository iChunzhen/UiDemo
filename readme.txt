专题一

1.activity启动流程
 
			 ActivityThread#main(String[] args)
			 
			 ActivityThread thread = new ActivityThread();
					thread.attach(false, startSeq);
					
					
			 IActivityManager mgr = ActivityManager.getService();
				  mgr.attachApplication(mAppThread, startSeq);		
				  
				ActivityManagerService#attachApplicationLocked  
				
				attachApplicationLocked(IApplicationThread thread,int pid, int callingUid, long startSeq) 
				 
				 thread.bindApplication(processName, appInfo, providers,……							//mStackSupervisor.attachApplicationLocked(app)
				 
				 
				android.app.ActivityThread.ApplicationThread#bindApplication
						sendMessage(H.BIND_APPLICATION, data);
						handleBindApplication(data);
						
						 mInstrumentation.onCreate(data.instrumentationArgs);
						 mInstrumentation.callApplicationOnCreate(app);
						 
						 
																										ActivityStackSupervisor#attachApplicationLocked
																											if (realStartActivityLocked(activity, app,top == activity /* andResume */, true /* checkConfig */)) 
																											
																												加入LaunchActivityItem	clientTransaction.addCallback(LaunchActivityItem.obtain(new Intent(r.intent),……
																												加入ResumeActivityItem或PauseActivityItem
																												mService.getLifecycleManager().scheduleTransaction(clientTransaction);
																												
																												
																												ClientLifecycleManager#scheduleTransaction(ClientTransaction)
																													transaction.schedule();
																													
																												ClientTransaction	scheduleTransaction
																												**********
																												android.app.ClientTransactionHandler#scheduleTransaction
																													sendMessage(ActivityThread.H.EXECUTE_TRANSACTION, transaction);
																													
																													 final ClientTransaction transaction = (ClientTransaction) msg.obj;
																													 mTransactionExecutor.execute(transaction);

																													 
																										 
2.view 绘制流程
		onMeasure()
		onLayout()
		onDraw()

																											
																										 
3.Paint
	属性配置
		mPaint = new Paint(); //初始化
		mPaint.setColor(Color.RED);// 设置颜色
		mPaint.setARGB(255, 255, 255, 0); // 设置 Paint对象颜色,范围为0~255
		mPaint.setAlpha(200); // 设置alpha不透明度,范围为0~255
		mPaint.setAntiAlias(true); // 抗锯齿
		mPaint.setStyle(Paint.Style.FILL); //描边效果
		mPaint.setStrokeWidth(4);//描边宽度
		mPaint.setStrokeCap(Paint.Cap.ROUND); //圆角效果
		mPaint.setStrokeJoin(Paint.Join.MITER);//拐角风格
		mPaint.setShader(new SweepGradient(200, 200, Color.BLUE, Color.RED)); //设置环形渲染器
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN)); //设置图层混合模式
		mPaint.setColorFilter(new LightingColorFilter(0x00ffff, 0x000000)); //设置颜色过滤器
		mPaint.setFilterBitmap(true); //设置双线性过滤
		mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));//设置画笔遮罩滤镜 ,传入度数和样式
		mPaint.setTextScaleX(2);// 设置文本缩放倍数
		mPaint.setTextSize(38);// 设置字体大小
		mPaint.setTextAlign(Paint.Align.LEFT);//对其方式
		mPaint.setUnderlineText(true);// 设置下划线
	设置着色器对象setShader(Shader shader)参数着色器对象，一般使用Shader的几个子类  画笔颜色渐变
		LinearGradient:线性渲染	渐变
		RadialGradient:环性渲染
		SweepGradient:扫描渲染
		BitmapShader:位图渲染
		ComposeShader:组合渲染		mShader = new ComposeShader(bitmapShader, linearGradient, PorterDuff.Mode.MULTIPLY);
			 mShader = new LinearGradient()
			 mPaint.setShader(mShader);
			 canvas.drawCircle（）
	设置颜色过滤setColorFilter(ColorFilter colorFilter)	 画笔颜色矩阵处理
		LightingColorFilter:光照效果
		PorterDuffColorFilter:指定一个颜色和一种PorterDuff.Mode与绘制对象进行合成
		ColorMatrixColorFilter:使用一个ColorMatrix来对颜色进行处理
			new ColorMatrixColorFilter(new ColorMatrix(colormatrix))//矩阵
			LightingColorFilter lighting = new LightingColorFilter(0xffffff,0x000000);
			mPaint.setColorFilter(lighting);											

	设置图层混合模式mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		两个图层！
		canvas.drawBitmap(createRectBitmap(mWidth, mHeight), 0, 0, mPaint);
			//设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
			//源图，重叠区域右下角部分
        canvas.drawBitmap(createCircleBitmap(mWidth, mHeight), 0, 0, mPaint);
			 //清除混合模式
        mPaint.setXfermode(null);
	
		18种混合模式
			
		
		 
4.canvas变换操作	变换操作后 后续操作继续在变换后的canvas上操作
		//1，平移操作	
			canvas.translate(50, 50) 将图像向右下平移
		 //2缩放操纵			
		 canvas.scale(0.5f, 0.5f);	xy坐标*0.5 图像变小并平移
		 canvas.scale(0.5f, 0.5f, 200,200)先缩放 再平移 平移距离为参数*缩放倍数
		 //3旋转操作
		 canvas.rotate(15, 650, 650); //px, py表示旋转中心的坐标
		 //4倾斜操作
		 canvas.skew(1, 0); //在X方向倾斜45度,Y轴逆时针旋转45
		 canvas.skew(0, 1); //在y方向倾斜45度,X轴顺时针旋转45
		 //5切割
		 canvas.clipRect(200, 200,700, 700); //画布被裁剪 坐标超出裁剪区域，无法绘制 坐标区域在裁剪范围内，绘制成功
		 canvas.clipOutRect(200,200,700,700); //画布裁剪外的区域 
		 //6矩阵Matrix
		 matrix.setTranslate(50,50);
		 matrix.setRotate(45);
         matrix.setScale(0.5f, 0.5f);
		 matrix.setValue()矩阵
        canvas.setMatrix(matrix);
	canvas变换粒子效果
	canvas启动页





5.path		
	op  filltype  direction moveTo rMoveTo相对
		lineTo 
		
		op  三种模式 DIFFERENCE路径1-路径2
						INTERSECT交集
						UNION联合路径+路径2
						XOR路径1与路径2不相交的部分
						REVERSE_DIFFERENCE	路径2-路径1
		setFillType
		FillType	WINDING		
					EVEN_ODD	路径1与路径2不相交的部分
					INVERSE_WINDING 反向绘制区域外
					INVERSE_EVEN_ODD	绘制相交的区域以及外部区域
		path常用api使用
			moveTo		移动下一次操作的起点位置
			lineTo		添加上一个点到当前点之间的直线到Path
			rMoveTo		不带r的方法是基于原点的坐标系(偏移量)， rXxx方法是基于当前点坐标系(偏移量)
			rLineTo
			close 起点终点连接 路径闭合
			添加子图形addXX
			addCircle添加圆
			addArc添加圆弧
			arcTo添加圆弧 
			addOval添加椭圆
			addRect添加矩形
			addRoundRect圆角矩形 用于圆角图片处理 使用图层混合模式
		
	贝塞尔曲线
		数据点 控制点
		一阶贝塞尔曲线
		path 连接贝塞尔曲线
			二阶	quadTo
				rQuadTo
			三阶cubicTo
				rCubicTo
			高阶	 两个公式
			
			
	PathMeasure需要关联一个创建好的path, forceClosed会影响Path的测量结果
		setPath(Path path, boolean forceClosed)//关联一个Path
		isClosed()//是否闭合
		getLength()//获取Path的长度
		nextContour()//跳转到下一个轮廓
		getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo)//截取片段
			返回值(boolean)	判断截取是否成功
			startD	开始截取位置距离 Path 起点的长度
			stopD	结束截取位置距离 Path 起点的长度
			dst		截取的 Path 将会添加到 dst 中
			startWithMoveTo	起始点是否使用 moveTo
		
		getPosTan(float distance, float[] pos, float[] tan)//获取指定长度的位置坐标及该点切线值
		getMatrix(float distance, Matrix matrix, int flags)//获取指定长度的位置坐标及该点Matrix
			distance	距离 Path 起点的长度
			matrix		根据 falgs 封装好的matrix
			falgs		规定哪些内容会存入到matrix中	可选择POSITION_MATRIX_FLAG(位置)、ANGENT_MATRIX_FLAG(正切)
					
6.事件分发源码分析
	事件定义 四种事件 down up move cancle
	事件序列
	事件分发对象
	主要方法 dispatchTouchEvent  onTouchEvent interce
	activity事件分发
		public boolean dispatchTouchEvent(MotionEvent ev) {
			if (ev.getAction() == MotionEvent.ACTION_DOWN) {
				onUserInteraction();//空方法子类可重写
			}
			if (getWindow().superDispatchTouchEvent(ev)) {
				//PhoneWindow对象 最终调用viewGroup的dispatchTouchEvent
				return true;
			}
			return onTouchEvent(ev);
		}
		
		public boolean onTouchEvent(MotionEvent event) {
			//判断是否超出了边界
			if (mWindow.shouldCloseOnTouch(this, event)) {
				finish();
				return true;
			}
			return false;
		}
		
	viewGroup事件分发
		public boolean dispatchTouchEvent(MotionEvent ev) {
			boolean handled = false;
			if (onFilterTouchEventForSecurity(ev)) {//屏蔽模糊事件
			
			}
			
		}
	
	view事件分发
	顺序：
	onTouchEvent
	onClickListener.onClick
	
	事件冲突解决办法
		
		外部滑动与内部滑动不一致：
		内外部方向一致：
		组合：
		解决步骤：
			制定合适的滑动策略：
			按策略分发事件：
				外部拦截法
				内部拦截法
7.动画
	VSYNCManager 模拟vsync信号 接收信号刷新页面
	
	ObjectAnimator 
			View target 记录对象作用的view
			创建FloatPropertyValuesHolder 	动画执行帮助类
			ofFloat()方法 根据时间进度计算动画执行进度(空间进度)
			接收vsync回调 
					统筹时间 空间进度
					根据设置的进度计算相应值进行重新绘制 holder和差值器计算
			设置时间差值器、时间			
	MyFloatPropertyValuesHolder	
			通过反射设置动画执行的方法（旋转平移缩放等）
			生成MyKeyframeSet 关键帧集合
	KeyframeSet		生成关键帧集合  
			创建TypeEvaluator 类型估值器
			根据对应时间在关键帧区间获取对应值（通过类型估值器计算）
	TimeInterpolator 时间估值器
	FloatKeyframe 保存帧数据
8.屏幕适配
9.MaterialDesign
10.自定义View

	自定义控件
			自绘控件
			组合控件
			继承控件
					事件类控件
					容器类控件
11.	手写 recyclerview
		回收池	Recycler	Stack存储item的view 后进先出的	push把项压入堆栈顶部
		适配器	Adapter
		recyclerview
		类比传送带
		回收策略 填充策略 
		
		布局测量 onMeasure
		布局摆放 onLayout
		滑动事件处理  onInterceptTouchEvent 拦截判断是否是上下滑动
					  onTouchEvent 根据滑动距离 调用滑动方法 scrollBy 
						根据滑动计算相应 添加移除item
						
		滑动处理 scrollBy	removeView
12.SVG矢量图创建不规则自定义控件
	创建ProviceItem 保存path 对应区域的颜色等值
	通过Android api解析SVG文件获取pathList 然后绘制
	分发事件
13.VLayout 阿里框架	定制抽取RecyclerView
14.PathMeasure高阶动画特效
		网易云打碟效果
		水波纹动画效果
	
	
	
	

																															 