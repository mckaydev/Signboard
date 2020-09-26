import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class CpuUsage {
    public static void main(String[] args) throws InterruptedException {

        while(true) {
            OperatingSystemMXBean osBean1 = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
//            OperatingSystemMXBean osBean2 = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//            String cpuUsage2 = String.format("%.2f", osBean2.getSystemCpuLoad() * 100);

            if (osBean1.getSystemCpuLoad() < 0) {
                continue;
            }

//            System.out.println(osBean1.getSystemCpuLoad());
//            String cpuUsage1 = String.format("%.2f", osBean1.getSystemCpuLoad() * 100);
            System.out.println("cpuUsage1: " + String.format("%.2f", osBean1.getSystemCpuLoad() * 100));
//            System.out.println("cpuUsage2: " + cpuUsage2);

            Thread.sleep(500);
        }
    }
}
