"use client";

import { useState, useEffect } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
} from "recharts";
import { getPriceHistory } from "@/lib/api";
import type { PricePoint } from "@/types/company";

interface PriceChartProps {
  stockCode: string;
}

const PERIODS = [
  { label: "1개월", value: "1M" },
  { label: "3개월", value: "3M" },
  { label: "6개월", value: "6M" },
];

// 2026-10-02 → 10.02
function formatDate(value: string) {
  const [, month, day] = String(value).split("-");
  return month && day ? `${month}.${day}` : String(value);
}

// 2026-10-02 → 2026.10.02
function formatFullDate(value: string) {
  return String(value).replace(/-/g, ".");
}

export default function PriceChart({ stockCode }: PriceChartProps) {
  const [period, setPeriod] = useState("1M");
  const [prices, setPrices] = useState<PricePoint[] | null>(null);

  useEffect(() => {
    let cancelled = false;

    getPriceHistory(stockCode, period)
      .then((data) => {
        if (cancelled) return;
        // 과거 → 현재 순서로 정렬
        const sorted = [...data.prices].sort((a, b) =>
          a.date.localeCompare(b.date),
        );
        setPrices(sorted);
      })
      .catch(() => {
        if (!cancelled) setPrices([]);
      });

    return () => {
      cancelled = true;
    };
  }, [stockCode, period]);

  const handlePeriodChange = (value: string) => {
    setPrices(null);
    setPeriod(value);
  };

  return (
    <div className="rounded-2xl border border-line bg-ink-2 px-6 py-5">
      <div className="mb-4 flex items-center">
        <span className="text-xs tracking-wide text-fg-3">시세 차트</span>

        <div className="ml-auto flex gap-1">
          {PERIODS.map((p) => (
            <button
              key={p.value}
              onClick={() => handlePeriodChange(p.value)}
              className={`rounded-md px-2.5 py-1 text-xs transition ${
                period === p.value
                  ? "bg-mint/[0.14] text-mint"
                  : "bg-ink-3 text-fg-3 hover:text-fg-2"
              }`}
            >
              {p.label}
            </button>
          ))}
        </div>
      </div>

      <div className="h-[240px]">
        {prices === null ? (
          <div className="flex h-full items-center justify-center text-sm text-fg-3">
            불러오는 중...
          </div>
        ) : prices.length === 0 ? (
          <div className="flex h-full items-center justify-center text-sm text-fg-3">
            시세 데이터를 불러올 수 없습니다
          </div>
        ) : (
          <ResponsiveContainer width="100%" height="100%">
            <LineChart
              data={prices}
              margin={{ top: 10, right: 16, left: 0, bottom: 5 }}
            >
              <XAxis
                dataKey="date"
                tick={{ fill: "#61728C", fontSize: 11 }}
                axisLine={false}
                tickLine={false}
                minTickGap={30}
                padding={{ left: 12, right: 12 }}
                tickFormatter={formatDate}
              />
              <YAxis
                tick={{ fill: "#61728C", fontSize: 11 }}
                axisLine={false}
                tickLine={false}
                width={55}
                tickCount={5}
                domain={[
                  (dataMin: number) => Math.floor(dataMin * 0.97),
                  (dataMax: number) => Math.ceil(dataMax * 1.03),
                ]}
                tickFormatter={(v) => v.toLocaleString()}
              />
              <Tooltip
                contentStyle={{
                  background: "#152036",
                  border: "1px solid #26324B",
                  borderRadius: 8,
                  fontSize: 12,
                }}
                labelStyle={{ color: "#9FB0C8" }}
                labelFormatter={(label) => String(label).replace(/-/g, ".")}
                formatter={(value) => [
                  `${Number(value).toLocaleString()}원`,
                  "종가",
                ]}
              />
              <Line
                type="monotone"
                dataKey="close"
                stroke="#3DD6A6"
                strokeWidth={2}
                dot={false}
              />
            </LineChart>
          </ResponsiveContainer>
        )}
      </div>
    </div>
  );
}
