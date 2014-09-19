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
	boolean isFirstLoc = true;// �Ƿ��״ζ�λ
	MapView mMapView = null;
	BaiduMap mBaiduMap = null;
	public Marker imark;
	public InfoWindow mInfoWindow;
	double yiwux=120.081906;
	double yiwuy=29.311901;
    LatLng yiwu = new LatLng(yiwux,yiwuy);  
	private RelativeLayout info;
	
	
	String[][] ibike={{"1","ũó��","29.306272","120.057986"},
			{"2","ũó���߲��г�","29.308929","120.061892"},
			{"3","�������������ڣ�","29.310838","120.066999"},
			{"4","ʤ�����г�","29.305636","120.063805"},
			{"5","ʤ��С��","29.305094","120.064277"},
			{"6","����·���г�","29.294816","120.059087"},
			{"7","���·��","29.296243","120.063161"},
			{"8","�Ϸ���","29.295419","120.067045"},
			{"9","�ִ���Ԣ","29.300285","120.068847"},
			{"10","ũ������","29.300397","120.071594"},
			{"11","�����Ƶ�","29.301407","120.072881"},
			{"12","����֤ȯ","29.303035","120.076505"},
			{"13","�����԰��","29.303166","120.071143"},
			{"14","�����԰��","29.305599","120.070628"},
			{"15","���������","29.306104","120.069105"},
			{"16","������","29.306983","120.072173"},
			{"17","������ͣ����","29.305561","120.077666"},
			{"18","��̩�ٻ�","29.30732","120.077302"},
			{"19","�»����","29.311923","120.073954"},
			{"20","������","29.313831","120.076636"},
			{"21","��������","29.315534","120.081271"},
			{"22","Т������԰","29.316776","120.082894"},
			{"23","������·ͣ����","29.307526","120.079877"},
			{"24","��۳�","29.309435","120.084061"},
			{"26","��԰�г�7����","29.299545","120.08122"},
			{"27","��԰�г�19����","29.30108","120.08167"},
			{"28","��������","29.294652","120.078718"},
			{"29","��԰�г�","29.298563","120.08361"},
			{"30","����·��","29.30543","120.088503"},
			{"31","��ó�����г�","29.31211","120.088846"},
			{"32","ס����","29.311231","120.094682"},
			{"33","���⹫԰","29.313233","120.096571"},
			{"34","���Ź�˾","29.314505","120.094082"},
			{"35","�����Ƶ�","29.316844","120.089554"},
			{"36","������ͷ","29.316844","120.100111"},
			{"43","��ó��һ������","29.325787","120.099489"},
			{"44","��ó��17����","29.330276","120.102193"},
			{"45","��������","29.331399","120.103544"},
			{"46","��ó��һ����","29.331062","120.103201"},
			{"47","������ҵ����","29.331885","120.102065"},
			{"48","��ó�Ƕ���29��","29.33312","120.10378"},
			{"49","��ó�Ƕ���45��","29.337815","120.106656"},
			{"50","�Ƹ�����A","29.338283","120.10815"},
			{"51","�Ƹ�����B","29.338675","120.108415"},
			{"52","��ó������55��","29.339873","120.107836"},
			{"53","�㽭̩¡����","29.342828","120.109016"},
			{"54","��ó������63��","29.343314","120.109939"},
			{"55","��ó������67��","29.343857","120.11157"},
			{"56","��ó������56��","29.340752","120.112514"},
			{"57","��ó������81��","29.341126","120.113265"},
			{"58","��ó�Ƕ���38��","29.335177","120.10951"},
			{"59","��ó��������2��","29.342791","120.116526"},
			{"60","��ó������84��","29.340247","120.118028"},
			{"61","��ó������101��","29.340303","120.119187"},
			{"62","��ó������104��","29.340321","120.121933"},
			{"63","��ó����������","29.337478","120.11496"},
			{"64","ʱ���㳡","29.304179","120.104226"},
			{"65","��ҽԺ","29.27458","120.03846"},
			{"66","��ʤ����վ","29.308453","120.109068"},
			{"67","������Ϣ����","29.30861","120.10113"},
			{"68","������߾�","29.326054","120.09335"},
			{"69","����Сѧ","29.334648","120.099483"},
			{"70","���������칫¥","29.337009","120.103171"},
			{"71","�������","29.296612","120.049154"},
			{"72","��ɯ����","29.29506","120.046084"},
			{"73","������","29.299878","120.045498"},
			{"74","���������칫¥","29.293097","120.048191"},
			{"75","���������칫¥","29.281401","120.042574"},
			{"76","���ֵ����´�","29.286552","120.039533"},
			{"77","����·103��","29.283973","120.054637"},
			{"78","���ÿ�����ѧУ","29.294946","120.061502"},
			{"79","˾������ɽ·","29.299313","120.065147"},
			{"80","���Ժ��ɽ·��","29.294806","120.063765"},
			{"81","��ˮ�","29.302016","120.047222"},
			{"82","ͯ�����","29.283064","120.050332"},
			{"83","��֤��","29.291964","120.067499"},
			{"84","��ͯ��԰��Ϫ·��","29.291309","120.069634"},
			{"85","ͨ����","29.310923","120.076547"},
			{"86","¥�´���г�","29.284635","120.057633"},
			{"87","����ѧ","29.289246","120.04705"},
			{"88","����ְУ","29.297127","120.043273"},
			{"89","������","29.301506","120.041575"},
			{"90","���ⶼ�й�Ԣ","29.302623","120.054494"},
			{"91","��ԷҽԺ","29.311029","120.061022"},
			{"92","��Ϫһ��","29.317734","120.063435"},
			{"93","��ʳ�մ���˾","29.301825","120.04032"},
			{"94","���·��","29.312866","120.080311"},
			{"95","վǰ�㳡","29.319864","120.080419"},
			{"96","��������","29.319473","120.088392"},
			{"97","��Ȩ������","29.317603","120.09322"},
			{"98","���ֲ��г�","29.319957","120.091653"},
			{"99","���ڸ۶���","29.345782","120.100333"},
			{"100","������","29.349128","120.113799"},
			{"101","���������칫¥","29.347961","120.118509"},
			{"102","��ó��������ĩվ","29.337495","120.118825"},
			{"103","���빫԰�̲�·","29.334257","120.118108"},
			{"104","���빫԰�̳Ǵ��","29.329893","120.112242"},
			{"105","��ó�ǹ�ί��","29.330113","120.108269"},
			{"106","�����´�","29.323952","120.074589"},
			{"107","�����´��嶫·","29.300131","120.091189"},
			{"108","�����嶫·","29.294086","120.095658"},
			{"109","��������","29.313846","120.121056"},
			{"110","��������","29.316176","120.128315"},
			{"111","��Դ���ӿƼ�԰","29.309161","120.135236"},
			{"112","���޲��г�","29.310304","120.120402"},
			{"113","���ݷ���","29.307689","120.113809"},
			{"114","�����ߺ�¥","29.307282","120.10215"},
			{"115","���з羰","29.30054","120.10592"},
			{"116","��������Ժ","29.303536","120.106283"},
			{"117","�����ֵ����´�","29.3044","120.10287"},
			{"118","�尮С��","29.302691","120.096697"},
			{"119","���ڶ���","29.297698","120.089523"},
			{"120","����ҽԺ","29.28825","120.081569"},
			{"121","���ϲ��г�","29.285325","120.074032"},
			{"122","����ҽԺ��","29.285936","120.080188"},
			{"123","������԰","29.276373","120.05796"},
			{"124","����һ��","29.31046","120.113003"},
			{"125","��ͨ�����","29.320374","120.046963"},
			{"126","��ԷѧУ","29.336072","120.045121"},
			{"127","��Ϫ��԰��","29.319895","120.05965"},
			{"128","�����г�","29.322794","120.072399"},
			{"129","��������ѩ��·","29.32509","120.066843"},
			{"130","��ɽͷ","29.329625","120.062564"},
			{"131","�������","29.334859","120.067144"},
			{"132","�����԰","29.332678","120.076175"},
			{"133","����С��","29.324035","120.089305"},
			{"134","���޻�԰�̲�·��","29.309047","120.123027"},
			{"135","���޻�԰�̲�·��","29.309667","120.124398"},
			{"136","��������","29.313901","120.124544"},
			{"137","��������칫¥","29.315258","120.130347"},
			{"138","���Ŷ�����������","29.346195","120.116561"},
			{"139","���ڸ۱�","29.347609","120.100258"},
			{"140","���������","29.333487","120.096869"},
			{"141","��������","29.327649","120.099603"},
			{"142","������һ��","29.336753","120.083797"},
			{"143","���ʼ�վ","29.331658","120.072526"},
			{"144","����·�϶�","29.326254","120.059625"},
			{"145","����ũ����","29.308806","120.054175"},
			{"146","ͯլ","29.312637","120.058221"},
			{"147","�ļ�����","29.316643","120.050184"},
			{"148","��Է������","29.320811","120.05136"},
			{"149","�ֹ�����","29.322868","120.05324"},
			{"150","���鳵һ��","29.334746","120.049769"},
			{"151","���������칫¥","29.306291","120.08015"},
			{"152","����Сѧ","29.319841","120.097785"},
			{"153","��������","29.325903","120.074538"},
			{"154","�������","29.329159","120.077187"},
			{"155","���ڵ��","29.332694","120.080135"},
			{"156","�����¶���","29.336674","120.081456"},
			{"157","ͼ���","29.313832","120.110251"},
			{"158","���޻�԰","29.306972","120.112704"},
			{"159","���������Ĺ㳡","29.284702","120.088919"},
			{"160","������С��","29.283265","120.084048"},
			{"161","������A��","29.287124","120.091386"},
			{"162","������Է","29.290735","120.089132"},
			{"163","��԰�Ŷ�","29.295602","120.085175"},
			{"164","����������","29.28803","120.09413"},
			{"165","÷԰���г�","29.296341","120.072655"},
			{"166","��ǻҽԺ","29.307951","120.071872"},
			{"167","��Է������","29.323962","120.057253"},
			{"168","���鳵����","29.336282","120.053403"},
			{"169","��ҵ԰","29.31057","120.0387"},
			{"170","��������","29.32767","120.072234"},
			{"171","�������","29.333171","120.069259"},
			{"172","��Է�ɳ���","29.327561","120.048004"},
			{"173","������������","29.325921","120.050875"},
			{"174","��Է�ֵ����´�","29.326269","120.053426"},
			{"175","��Ϫ��԰��","29.31914","120.060973"},
			{"176","�κ͹㳡","29.31165","120.056188"},
			{"177","��Է��������","29.313262","120.052618"},
			{"178","�������˻�԰","29.312246","120.049488"},
			{"179","��ͷ��԰","29.314569","120.064532"},
			{"180","��ɯһ��","29.29761","120.058161"},
			{"181","���󷢳���","29.293095","120.054065"},
			{"182","���������칫¥","29.288065","120.062352"},
			{"183","������������·","29.288155","120.066632"},
			{"184","��������԰","29.284652","120.064489"},
			{"185","����С","29.281085","120.054472"},
			{"186","��������԰","29.276368","120.048445"},
			{"187","�����Ĺ�԰","29.27221","120.042064"},
			{"188","��ɯ����","29.288322","120.028078"},
			{"189","�����","29.337253","120.090884"},
			{"190","��������","29.345711","120.105683"},
			{"191","���빫԰����·","29.335474","120.110201"},
			{"192","���빫԰����·","29.336769","120.115047"},
			{"193","�滮���Ժ","29.321985","120.104238"},
			{"194","���в��г�","29.321079","120.103048"},
			{"195","���ɽСѧ","29.309389","120.091954"},
			{"196","���ױ���Ժ","29.294951","120.075121"},
			{"197","����Ӱ��","29.298206","120.077512"},
			{"198","����ɽ��԰","29.300638","120.094262"},
			{"199","����ɽ��԰�嶫·","29.298362","120.093758"},
			{"200","�н��Ŷ�","29.291826","120.081355"},
			{"201","Խ��һ��","29.291211","120.088095"},
			{"202","������С��","29.282765","120.072364"},
			{"203","�����山","29.274291","120.050591"},
			{"204","��ڴ�ί��","29.312024","120.128314"},
			{"205","����ɽ�����칫¥","29.301977","120.102035"},
			{"206","�����ί��","29.29408","120.092529"},
			{"207","������","29.279525","120.062552"},
			{"208","ǰ��С��","29.283312","120.07948"},
			{"209","ӿ��㳡","29.283732","120.069503"},
			{"210","��ڱ���","29.314559","120.126991"},
			{"212","������һ��","29.294533","120.084514"},
			{"213","������ѧ��Ϫ��У","29.281426","120.064394"},
			{"214","ʵ��Сѧ","29.285695","120.077475"},
			{"215","�����칫��վ","29.315783","120.125047"},
			{"216","÷��������6����","29.310159","120.106097"},
			{"217","÷���������","29.311851","120.103434"},
			{"218","����·�й�����","29.311523","120.122887"},
			{"220","��ڲ��г�","29.310484","120.130926"},
			{"221","�����","29.312065","120.108806"}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext  
        //ע��÷���Ҫ��setContentView����֮ǰʵ��  
        SDKInitializer.initialize(getApplicationContext());         
		setContentView(R.layout.activity_main);
		
        //��ȡ��ͼ�ؼ�����  	   
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate city= MapStatusUpdateFactory.newLatLng(yiwu);
		mBaiduMap.setMapStatus(city);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
		mBaiduMap.setMapStatus(msu);		
		info = (RelativeLayout) findViewById(R.id.info);
		//��עλ��
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
                        //����InfoWindow  
                        mBaiduMap.hideInfoWindow();  
                    }  
                });
				mBaiduMap.showInfoWindow(mInfoWindow);
				popupInfo(info, id); 
				info.setVisibility(0);
				return true;
			}
		});
		// ������λͼ��		
		mBaiduMap.setMyLocationEnabled(true);
		// ��λ��ʼ��
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
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
		//����Markerͼ��  
		/*BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(R.drawable.icon_gcoding);  
		//����MarkerOption�������ڵ�ͼ�����Marker  
		OverlayOptions option = new MarkerOptions()  
		    .position(point)  
		    .icon(bitmap)
		    .zIndex(16);
		//�ڵ�ͼ�����Marker������ʾ  
		mBaiduMap.addOverlay(option);*/		    
		}
	}
	
	
	/**
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
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
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
		mLocClient.stop();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���         
        mMapView.onResume();  
		mLocClient.start();
		//mBaiduMap.setMyLocationEnabled(true);
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
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
