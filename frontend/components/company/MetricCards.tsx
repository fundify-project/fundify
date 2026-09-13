import type { Metric } from "@/types/company";

interface MetricCardsProps {
  metrics: Metric[];
}

// 판정에 따라 색상 결정
function badgeStyle(evaluation: string) {
  const positive = ["저평가", "우수", "양호"];
  return positive.includes(evaluation)
    ? "bg-mint/[0.13] text-mint"
    : "bg-coral/[0.13] text-coral";
}

// 지표에 따라 단위 붙이기
function formatValue(name: string, value: number) {
  const percentMetrics = ["ROE", "ROA", "부채비율", "유동비율", "영업이익률"];
  return percentMetrics.includes(name) ? `${value}%` : value.toFixed(1);
}

export default function MetricCards({ metrics }: MetricCardsProps) {
  return (
    <div className="rounded-2xl border border-line bg-ink-2 px-6 py-5">
      <div className="mb-4 text-xs tracking-wide text-fg-3">
        투자 지표 · 업종 비교
      </div>

      <div className="flex flex-col">
        {metrics.map((metric, i) => (
          <div
            key={metric.name}
            className={`flex items-center py-3.5 ${
              i !== metrics.length - 1 ? "border-b border-line/50" : ""
            }`}
          >
            <span className="w-24 text-sm text-fg-2">{metric.name}</span>

            <span className="text-base font-semibold text-fg">
              {formatValue(metric.name, metric.value)}
            </span>

            <span className="ml-3 text-xs text-fg-3">
              업종 {formatValue(metric.name, metric.industryAvg)}
            </span>

            <span
              className={`ml-auto rounded-full px-2.5 py-1 text-[11px] font-semibold ${badgeStyle(
                metric.evaluation,
              )}`}
            >
              {metric.evaluation}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
}
