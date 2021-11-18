package com.file.export.service.impl;

import com.deepoove.poi.data.MergeCellRule;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;
import com.file.export.service.ExportCompreAccessService;
import com.google.common.collect.Lists;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zz
 * @date 2021/9/15
 */
public class ExportCompreAccessServiceImpl implements ExportCompreAccessService {

	//private final AssessResultInfoService assessResultInfoService;
	//private final AssessItemService assessItemService;

	/**
	 * 表宽度
	 */
	private final float TABLE_WIDTH = 23.0f;

	/**
	 * 表数据
	 */
	public Map<String, Object> dataMap = new HashMap<>();

	@Override
	public Map<String, Object> getDataMap () {
		return this.dataMap;
	}

	@Override
	public void exportInitial(Long assessId, Long projectId) {
		try {
			List<Map<String, Object>> assessList = Lists.newArrayList(); //这里查询出业务数据
			//通用过滤器
			List<Map<String, Object>> firstFilter = assessList.stream().filter(e -> e.get("assessItemId") != null).collect(Collectors.toList());
			//通用过滤器
			List<Map<String, Object>> secondFilter = assessList.stream().filter(e -> e.get("assessItemId") != null && e.get("name") != null).collect(Collectors.toList());
			//汇总分支过滤器：过滤出包含总项汇总结果的集合
			List<Map<String, Object>> thirdFilter = assessList.stream().filter(e -> e.get("name") == null && e.get("assessItemId") != null).collect(Collectors.toList());
			Set<String> companySet = new LinkedHashSet<>();
			//条款名称集合
			Set<Object> assessSet = new LinkedHashSet<>();
			//专家对单个条款的评分结果集合
			List<String> assessResultList = new ArrayList<>();
			//结果集合
			List<Object> reviewResultList = new ArrayList<>();
			List<String> totalCollectResultList = new ArrayList<>();
			//条款描述集合
			Set<String> assessContentSet = new LinkedHashSet<>();
			//专家集合
			Set<String> expertSet = new LinkedHashSet<>();
			firstFilter.forEach(e -> {
				//过滤业务数据
			});
			secondFilter.forEach( e -> {
				expertSet.add(e.get("name").toString());
				assessResultList.add(e.get("assessResult").toString());
			});
			if (thirdFilter.size() > 0) {
				thirdFilter.forEach( e -> totalCollectResultList.add(e.get("assessResult").toString()));
			}
			//获取单项汇总
			ArrayList<String> collect = new ArrayList<>();
			List<Map<String, Object>> fourFilter = assessList.stream().filter((e ->
				e.get("name") == null
					&&e.get("assessName") == null
					&&e.get("content") != null
			)).collect(Collectors.toList());
			for (int i = 0; i < fourFilter.size(); ) {
				boolean flag = false;
				for (int j = 0; j < assessSet.size(); j++) {
					if ("1".equals(fourFilter.get(j).get("assessResult"))) {
						flag = true;
					}
				}
				collect.add(flag ? "合格" : "不合格");
				i+=assessSet.size();
			}

			//组装头部
			List<String> headerList = Lists.newArrayList("序号", "条款名称", "条款描述");
			headerList.addAll(companySet);
//            RowRenderData header = Rows.create(Arrays.stream(headerList.toArray()).toArray(String[]::new));
			RowRenderData header = Rows.of(Arrays.stream(headerList.toArray()).toArray(String[]::new)).center().textFontFamily("等线").textFontSize(10).textBold().create();

			ArrayList<RowRenderData> rowList = new ArrayList<>();
			rowList.add(header);


			//List<String> companyResult = assessResultList.stream().map( e -> e.replace("1", "通过").replace("2","不通过")).collect(Collectors.toList());

			//专家对单个条款的评分结果列表修正
            /*ArrayList<String> correctionResult = new ArrayList<>();
            int step = 0;
            for (int i = 0; i < companySet.size(); i++) {
                for (int j = 0; j < assessSet.size();j++) {
                    for (int k = 0; k <expertSet.size(); k++) {
                        correctionResult.add(assessResultList.get(j+ (assessSet.size()* k)+ step));
                    }
                }
                step += expertSet.size() * assessSet.size();
                // 221122 -> 212212   221221 ->222211
                // 2 1 2              2 2
                // 2 1 2              2 2
                //                    1 1
            }*/
			//专家对单个条款的评分结果列表修正
			ArrayList<String> correctionResult = new ArrayList<>();
			for (int i = 0; i < assessSet.size(); i++) {
				for (int j = 0; j < assessResultList.size(); ) {
					correctionResult.add(assessResultList.get(i + j));
					j+=assessSet.size();
				}
				// 221122 -> 212212   221221 ->222211
				// 2 1 2              2 2
				// 2 1 2              2 2
				//                    1 1
			}
			//总项汇总：汇总结果修正
			ArrayList<String> correctionTotalCollectResultList = new ArrayList<>();
			if (totalCollectResultList.size() > 0) {
				for (int i = 0; i < assessSet.size(); i++) {
					Integer index = 0;
					for (int j = 0; j < companySet.size(); j++) {
						correctionTotalCollectResultList.add(totalCollectResultList.get(i+ j+ index));
						index += assessSet.size() - 1;
					}
				}
			}

			ArrayList<Integer> perAssessCompanyResultList = new ArrayList<>();
			//计算每个条款的汇总结果
			for (int i = 0; i < correctionResult.size();) {
				Integer perAssessCompanyResult = 1;
				for (int j = 0; j < expertSet.size(); j++) {
					if (correctionResult.get(i+j).equals("2")) {
						perAssessCompanyResult = 2;
						break;
					}
				}
				perAssessCompanyResultList.add(perAssessCompanyResult);
				i += expertSet.size();
			}

			List<String> perAssessCompanyFinalResultList = perAssessCompanyResultList.stream().map(e -> e.equals(1) ? "通过" : "不通过").collect(Collectors.toList());
			List<String> perAssessCompanyFinalCollectResultList = correctionTotalCollectResultList.stream().map(e -> e.equals("1") ? "通过" : "不通过").collect(Collectors.toList());

			//组装行数据
			int index = 0;
			int size = companySet.size();
			for (int i = 0; i < assessSet.size(); i++) {
				List<String> contentList = Lists.newArrayList(String.valueOf(i + 1), assessSet.toArray()[i].toString(), assessContentSet.toArray()[i].toString());
				//总项汇总分支
				if (correctionTotalCollectResultList.size() > 0) {
					contentList.addAll(perAssessCompanyFinalCollectResultList.subList(index, size));
				}
				//单项汇总分支
				else {
					contentList.addAll(perAssessCompanyFinalResultList.subList(index, size));
				}
				RowRenderData row = Rows.of(Arrays.stream(contentList.toArray()).toArray(String[]::new)).center().textFontFamily("等线").textFontSize(10).create();
				rowList.add(row);
				index += companySet.size();
				size = index + companySet.size();
			}

			//组装尾行
			List<String> tailList = Lists.newArrayList("结论", null, null);
			for (Object o : reviewResultList) {
				// 为空时 单项汇总
				if (Objects.isNull(o)) {
					//tailList.add(null);
					tailList.addAll(collect);
					break;
				}
				else if (o.equals(true)) {
					tailList.add("通过");
				}
				else {
					tailList.add("不通过");
				}
			}
//            List<String> resultList = reviewResultList.stream().map(
//                    r -> r.equals(true) ? "通过" : "不通过").collect(Collectors.toList());
//            tailList.addAll(resultList);
			RowRenderData tailRow = Rows.of(Arrays.stream(tailList.toArray()).toArray(String[]::new)).center().textFontFamily("等线").textFontSize(10).create();
			rowList.add(tailRow);

			//合并单元格
			//动态生成行距
			ArrayList<Double> colCmWidths = Lists.newArrayList(14.63 / 11, 14.63 / 6, 14.63 / 3);
			for (int i = 0; i < companySet.size(); i++) {
				colCmWidths.add(14.63 / 6);
			}
			MergeCellRule rule = MergeCellRule.builder().map(MergeCellRule.Grid.of(assessSet.size() + 1, 0), MergeCellRule.Grid.of(assessSet.size() + 1, 2)).build();
			dataMap.put("initialTable", Tables.of(rowList.stream().toArray(RowRenderData[]::new)).mergeRule(rule).width(TABLE_WIDTH, colCmWidths.stream().mapToDouble(Double::doubleValue).toArray()).create());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取详细评审汇总表数据
	 */
	@Override
	public void exportDetail(Long assessId, Long projectId) {
		try {
			List<Map<String, Object>> detailAssessList = Lists.newArrayList(); //这里查询出业务数据
			Set<String> companySet = new LinkedHashSet<>();
			detailAssessList.forEach( d -> companySet.add(d.get("companyName").toString()));

			//构造header
			List<String> headerList = Lists.newArrayList("序号", "条款名称", "条款描述");
			headerList.addAll(companySet);
//            RowRenderData header = Rows.create(Arrays.stream(headerList.toArray()).toArray(String[]::new));
			RowRenderData header = Rows.of(Arrays.stream(headerList.toArray()).toArray(String[]::new)).center().textFontFamily("等线").textBold().textFontSize(10).create();

			List<Map<String, Object>> firstFilter = detailAssessList.stream().filter(e -> e.get("assessItemId") != null).collect(Collectors.toList());
			List<Map<String, Object>> secondFilter = detailAssessList.stream().filter(e -> e.get("assessItemId") != null && e.get("name") != null).collect(Collectors.toList());
			//专家集合
			Set<Object> expertSet = new LinkedHashSet<>();
			//条款集合
			Set<Object> assessSet = new LinkedHashSet<>();
			//专家评分集合
			List<String> assessResultList = new ArrayList<>(); //专家评分数据
			//条款描述集合
			Set<Object> assessContentSet = new LinkedHashSet<>();
			firstFilter.forEach(e -> {
				assessSet.add(e.get("parentContent"));
				assessContentSet.add((e.get("content")));
			});
			secondFilter.forEach( e -> {
				assessResultList.add(e.get("assessResult").toString());
				expertSet.add((e.get("name")));
			});
			//专家评分集合元素位置修正
			ArrayList<String> correctionAssessResultList = new ArrayList<>();
			for (int i = 0; i < assessSet.size(); i++) {
				for (int j = 0; j < assessResultList.size(); ) {
					correctionAssessResultList.add(assessResultList.get(i + j));
					j+=assessSet.size();
				}
				// 221122 -> 212212   221221 ->222211
				// 2 1 2              2 2
				// 2 1 2              2 2
				//                    1 1
			}

			//组装行数据
			int index = 0;
			int size = companySet.size();
			ArrayList<RowRenderData> rowList = new ArrayList<>();
			rowList.add(header);
			for (int i = 0; i < assessSet.size(); i++) {
				List<String> contentList = Lists.newArrayList(String.valueOf(i + 1), assessSet.toArray()[i].toString(), assessContentSet.toArray()[i].toString());

				for (int j = 0; j < companySet.size(); j++) {
					//所有专家给出的每个条款总分
					float totalGrade = 0;
					for (int k = 0; k < expertSet.size(); k++) {
						totalGrade += Float.parseFloat(correctionAssessResultList.get(k+index));
					}
					index += expertSet.size();
					contentList.add(new DecimalFormat("########.00").format(totalGrade));
				}

				RowRenderData row = Rows.of(Arrays.stream(contentList.toArray()).toArray(String[]::new)).center().textFontFamily("等线").textFontSize(10).create();
				rowList.add(row);

			}

			ArrayList<String> tailRowList = Lists.newArrayList("总分", null, null);
			ArrayList<Double> colCmWidths = Lists.newArrayList(14.63 / 11, 14.63 / 6, 14.63 / 3);

			for (int i = 0; i < assessResultList.size();) {
				//每位专家的总分
				float totalGrade = 0;
				for (int j = 0; j < assessResultList.size() / companySet.size(); j++) {
					totalGrade +=Float.parseFloat(assessResultList.get(i+j));
				}
				//汇总平均分
				// String averageGrade = new DecimalFormat("########.00").format(totalGrade / expertSet.size());
				tailRowList.add(new DecimalFormat("########.00").format(totalGrade));
				//动态生成行距
				i += assessResultList.size() / companySet.size();
				colCmWidths.add(14.63 / 6);
			}
			RowRenderData tailRow = Rows.of(Arrays.stream(tailRowList.toArray()).toArray(String[]::new)).center().textFontFamily("等线").textFontSize(10).create();
			rowList.add(tailRow);
			//合并单元格
			MergeCellRule rule = MergeCellRule.builder().map(MergeCellRule.Grid.of(assessSet.size() + 1, 0), MergeCellRule.Grid.of(assessSet.size() + 1, 2)).build();
			dataMap.put("detailTable", Tables.of(rowList.stream().toArray(RowRenderData[]::new)).mergeRule(rule).width(TABLE_WIDTH, colCmWidths.stream().mapToDouble(Double::doubleValue).toArray()).create());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取价格评审汇总表数据
	 */
	@Override
	public void exportPrice(Long assessId, Long projectId) {
		try {
			List<Map<String, Object>> detailAssessList = Lists.newArrayList(); //这里查询出业务数据
			List<String> offerAmount = new ArrayList<>();
			List<String> scopeList = new ArrayList<>();
			List<String> correctPriceList = new ArrayList<>();
			List<String> assessPriceList = new ArrayList<>();
			ArrayList<Object> headerList = new ArrayList<>();
			headerList.add("");
			detailAssessList.forEach(d -> {
				offerAmount.add(String.valueOf(d.get("offerAmount")));
				headerList.add(String.valueOf(d.get("companyName")));
				scopeList.add(String.valueOf(d.get("score")));
				correctPriceList.add(String.valueOf(d.get("priceAmount")));
				assessPriceList.add(String.valueOf(d.get("priceAmount")));
			});
			ArrayList<RowRenderData> rowList = new ArrayList<>();
			rowList.add(Rows.of(Arrays.stream(headerList.toArray()).toArray(String[]::new)).center().textFontFamily("等线").textBold().textFontSize(10).create());

			List<String> row1List = new ArrayList<>();
			row1List.add("开标价格");
			row1List.addAll(offerAmount);

			List<String> row2List = new ArrayList<>();
			row2List.add("修正价格");
			row2List.addAll(correctPriceList);

			List<String> row3List = new ArrayList<>();
			row3List.add("评标价格");
			row3List.addAll(assessPriceList);

			List<String> row4List = new ArrayList<>();
			row4List.add("得分");
			row4List.addAll(scopeList);

			rowList.add(Rows.of(Arrays.stream(row1List.toArray()).toArray(String[]::new)).center().textFontFamily("等线").textFontSize(10).create());
			rowList.add(Rows.of(Arrays.stream(row2List.toArray()).toArray(String[]::new)).center().textFontFamily("等线").textFontSize(10).create());
			rowList.add(Rows.of(Arrays.stream(row3List.toArray()).toArray(String[]::new)).center().textFontFamily("等线").textFontSize(10).create());
			rowList.add(Rows.of(Arrays.stream(row4List.toArray()).toArray(String[]::new)).center().textFontFamily("等线").textFontSize(10).create());

			//动态生成行距
			ArrayList<Double> colCmWidths = new ArrayList<>();
			for (int i = 0; i < headerList.size(); i++) {
				colCmWidths.add(14.63 / 6);
			}
			dataMap.put("priceTable", Tables.of(rowList.stream().toArray(RowRenderData[]::new)).width(TABLE_WIDTH, colCmWidths.stream().mapToDouble(Double::doubleValue).toArray()).create());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
