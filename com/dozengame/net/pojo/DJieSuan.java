package com.dozengame.net.pojo;

import java.util.ArrayList;
import java.util.HashMap;

public class DJieSuan {

	public static ArrayList m_siteList = new ArrayList(); // ��������б�
	public static ArrayList m_siteGold = new ArrayList(); // �����������ĳ���
	public static ArrayList m_winsiteList = new ArrayList(); // Ӯ���б�
	public static HashMap m_pondList = new HashMap(); // ����Ӯ�һ�òʳص���Ϣ
	public static HashMap m_bestPoke = new HashMap(); // ������ҵ���������
	public static HashMap m_diPoke = new HashMap(); // ��ֵ��׸�����ҵĵ���
	public static HashMap m_windGold = new HashMap(); // ����Ӯ��Ӯ��Ǯ
	public static HashMap m_pokeWeight = new HashMap(); // ������ҵ�����
	public static HashMap m_winPokeWeight = new HashMap(); // ����Ӯ�ҵ�����
	public static ArrayList m_finalBeskPoke = new ArrayList(); // ������������

	public static int m_mychipIn; // ���˱�������ע
	public static int m_mygainGold; // ���˱����ܻ��
	public static int m_mywinGold; // ���˱��ֹ���Ӯ�ã��ܻ�� - ����ע = ��Ӯ)
	public static int m_mycurTotalGold; // ���˵�ǰ���ܵ��ݶ�
	public static int m_choushui; // ϵͳ��ˮ������������
	public static int[] m_totalCaiChi = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // �ʳ���Ϣ
	// public static IDHall m_hall ;
	public static int m_iscomplete = 0; // �����־���Ƿ�������������������;�����˷���
	// �����������
	public static ArrayList m_itemList = new ArrayList();

	public DJieSuan() {

	}

	// ����Ϸ��ʼ��ʱ����ս�������
	public static void initData() {
		m_siteList.clear(); // ��������б�
		m_siteGold.clear(); // �����������ĳ���
		m_winsiteList.clear(); // Ӯ���б�
		m_pondList.clear(); // ����Ӯ�һ�òʳص���Ϣ
		m_bestPoke.clear(); // ����Ӯ�ҵ���������
		m_diPoke.clear(); // ����Ӯ�ҵĵ���
		m_windGold.clear(); // ����Ӯ��Ӯ��Ǯ
		m_pokeWeight.clear(); // ����Ӯ���ƴ�С
		m_finalBeskPoke.clear(); // ������������
		m_itemList.clear();
		int len = m_totalCaiChi.length;
		for (int i = 0; i < len; i++) {
			m_totalCaiChi[i] = 0;
		}
		m_mychipIn = 0;
		m_mygainGold = 0;
		m_mywinGold = 0;
		m_choushui = 5;
		m_mycurTotalGold = 0;

	}

	// public static void dealJieSuanData(ArrayList playerList, IDHall hall, int
	// mysite){
	//		
	// 
	// m_winsiteList.sort(sortbypokeweit);
	// int len=m_winsiteList.size();
	// for (int i = 0; i < len; i++){
	// HashMap obj = new HashMap();
	// int siteno = (Integer)m_winsiteList.get(i);
	// obj.put("userName", "");
	//		 
	// if(playerList[siteno])
	// obj["userName"] = playerList[siteno].nick;
	// obj["totalGold"] = m_windGold[siteno];
	// obj["poollist"] = m_pondList[siteno];
	// if (mysite == siteno) obj["isme"] = 1;
	// m_itemList[i] = obj;
	// //DTrace.traceex("dealJieSuanData::::obj::::", obj);
	// }
	// //DTrace.traceex("dealJieSuanData::itemlist::::", m_itemList);
	// m_mycurTotalGold = int(hall.getUserInfo().gold); //�ܵĵ��ݶ�
	// }

	// //�������أ��Ӵ�С����Ӯ���б�
	// public static int sortbypokeweit(site1:int, site2:int){
	// Number pokew1 = m_pokeWeight[site1];
	// Number pokew2 = m_pokeWeight[site2];
	// if (pokew1 < pokew2) return 1;
	// else if (pokew1 > pokew2) return -1;
	// else return 0;
	// }
	// ��Ϸ��־���Ƿ����ƽ�����
	public void setIscomplete(int setValue) {
		m_iscomplete = setValue;
	}

	public int getIscomplete() {
		return m_iscomplete;
	}
}
