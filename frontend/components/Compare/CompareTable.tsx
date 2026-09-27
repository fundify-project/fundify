import type { CompanyDetailResponse } from "@/types/company";

interface CompareTableProps {
  companies: CompanyDetailResponse[];
  onRemove: (stockCode: string) => void;
}

type Row = {
  label: string;
  getValue: (c: CompanyDetailResponse) => number | null;
  format: (v: number) => string;
  betterWhen: "high" | "low";
};

function findMetric(c: CompanyDetailResponse, name: string): number | null {
  return c.metrics?.find((m) => m.name === name)?.value ?? null;
}

function toJo(value: number) {
  return `${(value / 1_0000_0000_0000).toFixed(1)}조`;
}

const CATEGORIES: { title: string; rows: Row[] }[] = [
  {
    title: "밸류에이션",
    rows: [
      {
        label: "PER",
        getValue: (c) => findMetric(c, "PER"),
        format: (v) => v.toFixed(2),
        betterWhen: "low",
      },
      {
        label: "PBR",
        getValue: (c) => findMetric(c, "PBR"),
        format: (v) => v.toFixed(2),
        betterWhen: "low",
      },
    ],
  },
  {
    title: "수익성",
    rows: [
      {
        label: "ROE",
        getValue: (c) => findMetric(c, "ROE"),
        format: (v) => `${v.toFixed(2)}%`,
        betterWhen: "high",
      },
    ],
  },
  {
    title: "안정성",
    rows: [
      {
        label: "부채비율",
        getValue: (c) => findMetric(c, "부채비율"),
        format: (v) => `${v.toFixed(1)}%`,
        betterWhen: "low",
      },
    ],
  },
  {
    title: "재무 (최근)",
    rows: [
      {
        label: "매출액",
        getValue: (c) => c.financials?.[0]?.revenue ?? null,
        format: toJo,
        betterWhen: "high",
      },
      {
        label: "영업이익",
        getValue: (c) => c.financials?.[0]?.operatingProfit ?? null,
        format: toJo,
        betterWhen: "high",
      },
      {
        label: "당기순이익",
        getValue: (c) => c.financials?.[0]?.netIncome ?? null,
        format: toJo,
        betterWhen: "high",
      },
    ],
  },
];

function findBestIndex(values: (number | null)[], betterWhen: "high" | "low") {
  const valid = values.filter((v) => v !== null && v > 0);
  if (valid.length < 2) return -1;

  let bestIndex = -1;
  let bestValue: number | null = null;

  values.forEach((v, i) => {
    if (v === null || v <= 0) return;
    if (
      bestValue === null ||
      (betterWhen === "high" ? v > bestValue : v < bestValue)
    ) {
      bestValue = v;
      bestIndex = i;
    }
  });

  return bestIndex;
}

export default function CompareTable({
  companies,
  onRemove,
}: CompareTableProps) {
  const gridStyle = {
    gridTemplateColumns: `130px repeat(${companies.length}, 1fr)`,
  };

  return (
    <div className="overflow-hidden rounded-2xl border border-line bg-ink-2">
      {/* 기업명 헤더 */}
      <div className="grid border-b border-line" style={gridStyle}>
        <div className="px-4 py-4 text-s text-fg-3">항목</div>
        {companies.map((c) => (
          <div key={c.info.stockCode} className="px-4 py-4 text-center">
            <div className="font-semibold text-fg">{c.info.corpName}</div>
            <div className="mt-0.5 text-xs text-fg-3">{c.info.stockCode}</div>
            <button
              onClick={() => onRemove(c.info.stockCode)}
              className="mt-1.5 text-xs text-fg-3 transition hover:text-coral"
            >
              제거
            </button>
          </div>
        ))}
      </div>

      {/* 카테고리별 행 */}
      {CATEGORIES.map((category) => (
        <div key={category.title}>
          <div className="border-b border-line bg-mint/[0.05] px-4 py-2 text-xs font-semibold text-mint">
            {category.title}
          </div>

          {category.rows.map((row) => {
            const values = companies.map((c) => row.getValue(c));
            const bestIndex = findBestIndex(values, row.betterWhen);

            return (
              <div
                key={row.label}
                className="grid border-b border-line/40"
                style={gridStyle}
              >
                <div className="px-4 py-3.5 text-sm text-fg-2">{row.label}</div>
                {values.map((v, i) => (
                  <div
                    key={i}
                    className={`px-4 py-3.5 text-center text-sm font-medium ${
                      i === bestIndex ? "text-mint" : "text-fg"
                    }`}
                  >
                    {v === null ? "-" : row.format(v)}
                  </div>
                ))}
              </div>
            );
          })}
        </div>
      ))}
    </div>
  );
}
