package com.flourix.ibikeyiwu;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Overlay;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler; 
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	boolean isFirstLoc = true;// 是否首次定位
	MapView mMapView = null;
	BaiduMap mBaiduMap = null;
	public Marker imark;
	public InfoWindow mInfoWindow;
	double yiwux=120.081906;
	double yiwuy=29.311901;
    LatLng yiwu = new LatLng(yiwux,yiwuy);  
	private RelativeLayout info;
	
	
	String[][] ibike={{"1","农贸城","29.306272","120.057986"},
			{"2","农贸城蔬菜市场","29.308929","120.061892"},
			{"3","建设社区（二期）","29.310838","120.066999"},
			{"4","胜利菜市场","29.305636","120.063805"},
			{"5","胜利小区","29.305094","120.064277"},
			{"6","贝村路菜市场","29.294816","120.059087"},
			{"7","宏迪路口","29.296243","120.063161"},
			{"8","南方联","29.295419","120.067045"},
			{"9","现代公寓","29.300285","120.068847"},
			{"10","农商银行","29.300397","120.071594"},
			{"11","伊美酒店","29.301407","120.072881"},
			{"12","中信证券","29.303035","120.076505"},
			{"13","绣湖公园南","29.303166","120.071143"},
			{"14","绣湖公园北","29.305599","120.070628"},
			{"15","绣湖体育馆","29.306104","120.069105"},
			{"16","湖清门","29.306983","120.072173"},
			{"17","朝阳门停车场","29.305561","120.077666"},
			{"18","银泰百货","29.30732","120.077302"},
			{"19","新华书店","29.311923","120.073954"},
			{"20","公安局","29.313831","120.076636"},
			{"21","富国超市","29.315534","120.081271"},
			{"22","孝子祠公园","29.316776","120.082894"},
			{"23","工人西路停车场","29.307526","120.079877"},
			{"24","香港城","29.309435","120.084061"},
			{"26","篁园市场7号门","29.299545","120.08122"},
			{"27","篁园市场19号门","29.30108","120.08167"},
			{"28","花鸟世界","29.294652","120.078718"},
			{"29","篁园市场","29.298563","120.08361"},
			{"30","化工路口","29.30543","120.088503"},
			{"31","商贸区菜市场","29.31211","120.088846"},
			{"32","住建局","29.311231","120.094682"},
			{"33","主题公园","29.313233","120.096571"},
			{"34","电信公司","29.314505","120.094082"},
			{"35","银都酒店","29.316844","120.089554"},
			{"36","宗泽桥头","29.316844","120.100111"},
			{"43","商贸城一区西门","29.325787","120.099489"},
			{"44","商贸城17号门","29.330276","120.102193"},
			{"45","浙商银行","29.331399","120.103544"},
			{"46","商贸城一区北","29.331062","120.103201"},
			{"47","稠州商业银行","29.331885","120.102065"},
			{"48","商贸城二区29门","29.33312","120.10378"},
			{"49","商贸城二区45门","29.337815","120.106656"},
			{"50","财富大厦A","29.338283","120.10815"},
			{"51","财富大厦B","29.338675","120.108415"},
			{"52","商贸城三区55门","29.339873","120.107836"},
			{"53","浙江泰隆银行","29.342828","120.109016"},
			{"54","商贸城三区63门","29.343314","120.109939"},
			{"55","商贸城三区67门","29.343857","120.11157"},
			{"56","商贸城三区56门","29.340752","120.112514"},
			{"57","商贸城四区81门","29.341126","120.113265"},
			{"58","商贸城二区38门","29.335177","120.10951"},
			{"59","商贸城四区北2门","29.342791","120.116526"},
			{"60","商贸城四区84门","29.340247","120.118028"},
			{"61","商贸城五区101门","29.340303","120.119187"},
			{"62","商贸城五区104门","29.340321","120.121933"},
			{"63","商贸城四区南门","29.337478","120.11496"},
			{"64","时代广场","29.304179","120.104226"},
			{"65","稠江医院","29.27458","120.03846"},
			{"66","永胜公交站","29.308453","120.109068"},
			{"67","地理信息中心","29.30861","120.10113"},
			{"68","检验检疫局","29.326054","120.09335"},
			{"69","福田小学","29.334648","120.099483"},
			{"70","福田社区办公楼","29.337009","120.103171"},
			{"71","王斌相框","29.296612","120.049154"},
			{"72","浪莎二期","29.29506","120.046084"},
			{"73","松门里","29.299878","120.045498"},
			{"74","黎明社区办公楼","29.293097","120.048191"},
			{"75","昌德社区办公楼","29.281401","120.042574"},
			{"76","稠江街道办事处","29.286552","120.039533"},
			{"77","昌德路103号","29.283973","120.054637"},
			{"78","经济开发区学校","29.294946","120.061502"},
			{"79","司法局香山路","29.299313","120.065147"},
			{"80","检察院香山路口","29.294806","120.063765"},
			{"81","大水畈","29.302016","120.047222"},
			{"82","童店二区","29.283064","120.050332"},
			{"83","公证处","29.291964","120.067499"},
			{"84","儿童公园丹溪路口","29.291309","120.069634"},
			{"85","通惠门","29.310923","120.076547"},
			{"86","楼下村菜市场","29.284635","120.057633"},
			{"87","稠江中学","29.289246","120.04705"},
			{"88","城镇职校","29.297127","120.043273"},
			{"89","五里松","29.301506","120.041575"},
			{"90","阳光都市公寓","29.302623","120.054494"},
			{"91","北苑医院","29.311029","120.061022"},
			{"92","丹溪一区","29.317734","120.063435"},
			{"93","粮食收储公司","29.301825","120.04032"},
			{"94","五叉路口","29.312866","120.080311"},
			{"95","站前广场","29.319864","120.080419"},
			{"96","词林社区","29.319473","120.088392"},
			{"97","产权交易所","29.317603","120.09322"},
			{"98","词林菜市场","29.319957","120.091653"},
			{"99","义乌港东南","29.345782","120.100333"},
			{"100","草塘沿","29.349128","120.113799"},
			{"101","诚信社区办公楼","29.347961","120.118509"},
			{"102","商贸城五区首末站","29.337495","120.118825"},
			{"103","中央公园商博路","29.334257","120.118108"},
			{"104","中央公园商城大道","29.329893","120.112242"},
			{"105","商贸城管委会","29.330113","120.108269"},
			{"106","怡乐新村","29.323952","120.074589"},
			{"107","江东新村义东路","29.300131","120.091189"},
			{"108","樊村义东路","29.294086","120.095658"},
			{"109","下王二区","29.313846","120.121056"},
			{"110","伊美望族","29.316176","120.128315"},
			{"111","淘源电子科技园","29.309161","120.135236"},
			{"112","东洲菜市场","29.310304","120.120402"},
			{"113","九州房产","29.307689","120.113809"},
			{"114","行政七号楼","29.307282","120.10215"},
			{"115","城市风景","29.30054","120.10592"},
			{"116","江东卫生院","29.303536","120.106283"},
			{"117","江东街道办事处","29.3044","120.10287"},
			{"118","五爱小区","29.302691","120.096697"},
			{"119","义乌二中","29.297698","120.089523"},
			{"120","中心医院","29.28825","120.081569"},
			{"121","江南菜市场","29.285325","120.074032"},
			{"122","中心医院南","29.285936","120.080188"},
			{"123","钓鱼矶公园","29.276373","120.05796"},
			{"124","下王一区","29.31046","120.113003"},
			{"125","交通运输局","29.320374","120.046963"},
			{"126","北苑学校","29.336072","120.045121"},
			{"127","丹溪公园北","29.319895","120.05965"},
			{"128","物资市场","29.322794","120.072399"},
			{"129","物流中心雪峰路","29.32509","120.066843"},
			{"130","下山头","29.329625","120.062564"},
			{"131","下里角塘","29.334859","120.067144"},
			{"132","惠民家园","29.332678","120.076175"},
			{"133","阳光小区","29.324035","120.089305"},
			{"134","东洲花园商博路西","29.309047","120.123027"},
			{"135","东洲花园商博路东","29.309667","120.124398"},
			{"136","下王三区","29.313901","120.124544"},
			{"137","后湖社区办公楼","29.315258","120.130347"},
			{"138","诚信二区富国超市","29.346195","120.116561"},
			{"139","义乌港北","29.347609","120.100258"},
			{"140","竹佳里社区","29.333487","120.096869"},
			{"141","长春社区","29.327649","120.099603"},
			{"142","大塘下一区","29.336753","120.083797"},
			{"143","市质检站","29.331658","120.072526"},
			{"144","景三路南端","29.326254","120.059625"},
			{"145","复兴农商行","29.308806","120.054175"},
			{"146","童宅","29.312637","120.058221"},
			{"147","四季二区","29.316643","120.050184"},
			{"148","北苑供电所","29.320811","120.05136"},
			{"149","乐购超市","29.322868","120.05324"},
			{"150","何麻车一区","29.334746","120.049769"},
			{"151","向阳社区办公楼","29.306291","120.08015"},
			{"152","宾王小学","29.319841","120.097785"},
			{"153","义乌三中","29.325903","120.074538"},
			{"154","交警大队","29.329159","120.077187"},
			{"155","义乌电大","29.332694","120.080135"},
			{"156","大塘下二区","29.336674","120.081456"},
			{"157","图书馆","29.313832","120.110251"},
			{"158","东洲花园","29.306972","120.112704"},
			{"159","青岩刘中心广场","29.284702","120.088919"},
			{"160","江南三小区","29.283265","120.084048"},
			{"161","青岩刘A区","29.287124","120.091386"},
			{"162","江东商苑","29.290735","120.089132"},
			{"163","篁园桥东","29.295602","120.085175"},
			{"164","江东供电所","29.28803","120.09413"},
			{"165","梅园菜市场","29.296341","120.072655"},
			{"166","口腔医院","29.307951","120.071872"},
			{"167","北苑工商所","29.323962","120.057253"},
			{"168","何麻车二区","29.336282","120.053403"},
			{"169","创业园","29.31057","120.0387"},
			{"170","市政管理处","29.32767","120.072234"},
			{"171","上里角塘","29.333171","120.069259"},
			{"172","北苑派出所","29.327561","120.048004"},
			{"173","行政服务中心","29.325921","120.050875"},
			{"174","北苑街道办事处","29.326269","120.053426"},
			{"175","丹溪公园南","29.31914","120.060973"},
			{"176","嘉和广场","29.31165","120.056188"},
			{"177","北苑世纪联华","29.313262","120.052618"},
			{"178","城市名人花园","29.312246","120.049488"},
			{"179","火车头公园","29.314569","120.064532"},
			{"180","浪莎一期","29.29761","120.058161"},
			{"181","大润发超市","29.293095","120.054065"},
			{"182","锦都社区办公楼","29.288065","120.062352"},
			{"183","锦都别墅西江路","29.288155","120.066632"},
			{"184","江滨二公园","29.284652","120.064489"},
			{"185","稠江二小","29.281085","120.054472"},
			{"186","江滨三公园","29.276368","120.048445"},
			{"187","江滨四公园","29.27221","120.042064"},
			{"188","浪莎三期","29.288322","120.028078"},
			{"189","气象局","29.337253","120.090884"},
			{"190","银海二区","29.345711","120.105683"},
			{"191","中央公园福田路","29.335474","120.110201"},
			{"192","中央公园银海路","29.336769","120.115047"},
			{"193","规划设计院","29.321985","120.104238"},
			{"194","兴中菜市场","29.321079","120.103048"},
			{"195","义驾山小学","29.309389","120.091954"},
			{"196","妇幼保健院","29.294951","120.075121"},
			{"197","义乌影都","29.298206","120.077512"},
			{"198","鸡鸣山公园","29.300638","120.094262"},
			{"199","鸡鸣山公园义东路","29.298362","120.093758"},
			{"200","中江桥东","29.291826","120.081355"},
			{"201","越阳一区","29.291211","120.088095"},
			{"202","江南四小区","29.282765","120.072364"},
			{"203","银河湾北","29.274291","120.050591"},
			{"204","青口村委会","29.312024","120.128314"},
			{"205","鸡鸣山社区办公楼","29.301977","120.102035"},
			{"206","樊村居委会","29.29408","120.092529"},
			{"207","刑侦大队","29.279525","120.062552"},
			{"208","前成小区","29.283312","120.07948"},
			{"209","涌金广场","29.283732","120.069503"},
			{"210","青口北区","29.314559","120.126991"},
			{"212","龚大塘一区","29.294533","120.084514"},
			{"213","稠州中学丹溪分校","29.281426","120.064394"},
			{"214","实验小学","29.285695","120.077475"},
			{"215","南下朱公交站","29.315783","120.125047"},
			{"216","梅湖体育场6号门","29.310159","120.106097"},
			{"217","梅湖体彩中心","29.311851","120.103434"},
			{"218","东洲路中国银行","29.311523","120.122887"},
			{"220","青口菜市场","29.310484","120.130926"},
			{"221","篮球馆","29.312065","120.108806"}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext());         
		setContentView(R.layout.activity_main);
		
        //获取地图控件引用  	   
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate city= MapStatusUpdateFactory.newLatLng(yiwu);
		mBaiduMap.setMapStatus(city);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
		mBaiduMap.setMapStatus(msu);		
		info = (RelativeLayout) findViewById(R.id.info);
		//标注位置
		initOverlay();		
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(final Marker marker) {
				Button button = new Button(getApplicationContext());
				button.setBackgroundResource(R.drawable.popup);
				final LatLng ll = marker.getPosition();
                String id=marker.getTitle();
                button.setText(ibike[Integer.parseInt(id)][1]);
				mInfoWindow = new InfoWindow(button, ll,  
						new OnInfoWindowClickListener()  
                {  
					  
                    @Override  
                    public void onInfoWindowClick()  
                    {  
                        //隐藏InfoWindow  
                        mBaiduMap.hideInfoWindow();  
                    }  
                });
				mBaiduMap.showInfoWindow(mInfoWindow);
				popupInfo(info, id); 
				info.setVisibility(0);
				return true;
			}
		});
		// 开启定位图层		
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		
		mBaiduMap.setOnMapClickListener(new OnMapClickListener()
		{

			@Override
			public boolean onMapPoiClick(MapPoi arg0)
			{
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0)
			{
				info.setVisibility(View.GONE);
				mBaiduMap.hideInfoWindow();

			}
		});
	  }
	

    protected void popupInfo(RelativeLayout info,String id)  
    {   
    	String jie="<div><img src='http://218.93.33.59:85/map/yiwumap/ibikegif.asp?id="+ibike[Integer.parseInt(id)][0]+"&flag=1' /></div>";
    	String huan="<div><img src='http://218.93.33.59:85/map/yiwumap/ibikegif.asp?id="+ibike[Integer.parseInt(id)][0]+"&flag=2' /><div>";
    	TextView infoid;
    	TextView infoname;
    	infoid=(TextView)info.findViewById(R.id.bh);
    	infoname=(TextView)info.findViewById(R.id.mc);
    	infoid.setText(ibike[Integer.parseInt(id)][0]);
    	infoname.setText(ibike[Integer.parseInt(id)][1]);
    	//new DownLoadImage((ImageView) findViewById(R.id.imageView1)).execute(jie);   
    	//new DownLoadImage((ImageView) findViewById(R.id.imageView2)).execute(huan);
    	WebView kejie = (WebView) findViewById(R.id.webView1);
		kejie.loadDataWithBaseURL(null, jie, "text/html", "UTF-8",null );
        WebView kehuan=(WebView) findViewById(R.id.webView2);
        kehuan.loadDataWithBaseURL(null, huan, "text/html", "UTF-8",null );
    }  
	
       
   
    /*public class DownLoadImage extends AsyncTask<String, Void, Bitmap> {  
        ImageView imageView;  
      
        public DownLoadImage(ImageView imageView) {  
            // TODO Auto-generated constructor stub  
            this.imageView = imageView;  
        }  
      
        @Override  
        protected Bitmap doInBackground(String... urls) {  
            // TODO Auto-generated method stub  
            String url = urls[0];  
            Bitmap tmpBitmap = null;  
            try {  
                InputStream is = new java.net.URL(url).openStream();  
                tmpBitmap = BitmapFactory.decodeStream(is);  
            } catch (Exception e) {  
                e.printStackTrace();  
                Log.i("test", e.getMessage());  
            }  
            return tmpBitmap;  
        }  
      
        @Override  
        protected void onPostExecute(Bitmap result) {  
            // TODO Auto-generated method stub  
            imageView.setImageBitmap(result);  
        }  
    }*/ 
    
	public void initOverlay() {
		BitmapDescriptor bitmap = BitmapDescriptorFactory  
			    .fromResource(R.drawable.icon_gcoding);  
		for(int i=0;i<211;i++){		
			double addlat=0.005983;
			double addlng=0.006450;
			double lat=Double.parseDouble(ibike[i][2])+addlat;
			double lng=Double.parseDouble(ibike[i][3])+addlng;	
		    LatLng point = new LatLng(lat,lng);  
			OverlayOptions option = new MarkerOptions()
			        .position(point)
			        .icon(bitmap)
			        .title(String.valueOf(i))
					.zIndex(16).draggable(false);
			imark = (Marker) (mBaiduMap.addOverlay(option));			
		//构建Marker图标  
		/*BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(R.drawable.icon_gcoding);  
		//构建MarkerOption，用于在地图上添加Marker  
		OverlayOptions option = new MarkerOptions()  
		    .position(point)  
		    .icon(bitmap)
		    .zIndex(16);
		//在地图上添加Marker，并显示  
		mBaiduMap.addOverlay(option);*/		    
		}
	}
	
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			mBaiduMap
			.setMyLocationConfigeration(new MyLocationConfiguration(
					LocationMode.NORMAL, true, null));
			
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
    @Override
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理         
        mMapView.onResume();  
		mLocClient.start();
		//mBaiduMap.setMyLocationEnabled(true);
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
		mLocClient.stop();
		//mBaiduMap.setMyLocationEnabled(false);
        }  
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
