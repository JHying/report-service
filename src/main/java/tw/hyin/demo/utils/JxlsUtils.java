package tw.hyin.demo.utils;

import org.apache.commons.jexl3.JexlBuilder;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import tw.hyin.java.utils.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author H-yin on 2022.
 */
public class JxlsUtils {

    public static void exportExcel(InputStream template, OutputStream os, Map<String, Object> model) throws IOException {
        Context context = new Context();
        if (model != null) {
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                context.putVar(entry.getKey(), entry.getValue());
            }
        }
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformer = jxlsHelper.createTransformer(template, os);
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
        Map<String, Object> funcs = new HashMap<>();
        funcs.put("utils", new JxlsUtils()); // 新增自定義功能
        JexlBuilder jb = new JexlBuilder();
        evaluator.setJexlEngine(jb.namespaces(funcs).create());
        jxlsHelper.processTemplate(context, transformer);
        //必須有這個表格的函式統計才不會出錯
        jxlsHelper.setUseFastFormulaProcessor(false).processTemplate(context, transformer);
    }

    // 日期格式化
    public String dateFmt(Long secTimestamp, String fmt) {
        if (secTimestamp == null) {
            return "";
        }
        try {
            SimpleDateFormat dateFmt = new SimpleDateFormat(fmt);
            return dateFmt.format(new Date(secTimestamp * 1000));
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return "";
    }

    // 日期格式化
    public String dateFmt(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat dateFmt = new SimpleDateFormat(fmt);
            return dateFmt.format(date);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return "";
    }

    // 時間差計算
    public Long dateDiff(Date endDate, Date startDate) {
        if (endDate == null || startDate == null) {
            return null;
        }
        try {
            Long diff = endDate.getTime() - startDate.getTime();
            return diff / 1000 / 60;
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return null;
    }

    // if判断
    public Object ifelse(boolean b, Object o1, Object o2) {
        return b ? o1 : o2;
    }

}
